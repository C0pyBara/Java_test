import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.*;

public class CrptApi {

    private final String apiUrl = "https://ismp.crpt.ru/api/v3/lk/documents/create";
    private final BlockingQueue<Instant> requestQueue;
    private final TimeUnit timeUnit;
    private final int requestLimit;
    private final ObjectMapper objectMapper;
    private final OkHttpClient httpClient;

    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this.timeUnit = timeUnit;
        this.requestLimit = requestLimit;
        this.requestQueue = new LinkedBlockingQueue<>();
        this.objectMapper = new ObjectMapper();
        this.httpClient = new OkHttpClient();
    }

    public void createGoodsIntroductionDocument(Document document, String signature) throws CrptApiException {
        try {
            String json = objectMapper.writeValueAsString(document);
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                    .addHeader("Authorization", "Bearer " + signature)
                    .build();

            acquireRequestPermit();

            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new CrptApiException("Error creating document: " + response.message());
                }
            }
        } catch (JsonProcessingException e) {
            throw new CrptApiException("Error serializing document to JSON: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new CrptApiException("Interrupted while waiting for request permit: " + e.getMessage());
        } finally {
            releaseRequestPermit();
        }
    }

    private void acquireRequestPermit() throws InterruptedException {
        requestQueue.put(Instant.now());

        while (requestQueue.size() > requestLimit) {
            Instant firstRequestTime = requestQueue.peek();
            if (Instant.now().minus(firstRequestTime).toMillis() > timeUnit.toMillis(1)) {
                requestQueue.poll();
            } else {
                Thread.sleep(timeUnit.toMillis(1));
            }
        }
    }

    private void releaseRequestPermit() {
        requestQueue.poll();
    }

    private static class Document {
        private String description;
        private String docId;
        private String docStatus;
        private String docType;
        private boolean importRequest;
        private String ownerInn;
        private String participantInn;
        private String producerInn;
        private String productionDate;
        private String productionType;
        private List<Product> products;
        private String regDate;
        private String regNumber;

        // Getters and setters omitted for brevity
    }

    private static class Product {
        private String certificateDocument;
        private String certificateDocumentDate;
        private String certificateDocumentNumber;
        private String ownerInn;
        private String producerInn;
        private String productionDate;
        private String tnvedCode;
        private String uitCode;
        private String uituCode;

        // Getters and setters omitted for brevity
    }

    private static class CrptApiException extends Exception {
        public CrptApiException(String message) {
            super(message);
        }
    }
}

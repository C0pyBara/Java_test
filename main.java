from django.contrib import admin
from .models import SliderItem

admin.site.register(SliderItem, admin.ModelAdmin(
    list_display=('image_tag', 'title', 'order'),
    ordering=('order',),
))

from rest_framework import serializers

from .models import User

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'userType', 'agriServiceCenter', 'gramaNiladhariDiv', 
        'regNo', 'name', 'address', 'phone', 'nic', 'preferredLocale']
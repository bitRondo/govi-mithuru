from django.utils import timezone
from django.db import models

from django.contrib.auth.models import AbstractBaseUser, PermissionsMixin

from accountManagement.managers import UserManager

class User (AbstractBaseUser, PermissionsMixin):
    USER_TYPE_CHOICES = [
        (None, '- Select -'),
        (0, 'Admin'),
        (1, 'Farmer'),
        (2, 'AgriOfficer'),
    ]

    userType = models.SmallIntegerField(
        'User type',
        choices=USER_TYPE_CHOICES,
        help_text='Required',
    )

    agriServiceCenter = models.CharField(
        'Agriculture Service Center the user belongs to',
        max_length=150,
        help_text='Required',
    )

    gramaNiladhariDiv = models.CharField(
        'Grama Niladhari Division of the user',
        max_length=150,
        blank=True,
    )

    regNo = models.CharField(
        'Registration Number',
        max_length=200,
        help_text='Required',
        unique=True,
    )

    name = models.CharField(
        'Full name of the user',
        max_length=250,
        help_text='Required',
    )

    address = models.CharField(
        'Address of residence',
        max_length=250,
        blank=True,
    )

    phone = models.CharField(
        'Phone number',
        max_length=15,
        help_text='Required',
    )

    nic = models.CharField(
        'National Identity Card number',
        max_length=25,
        help_text='Required',
    )

    preferredLocale = models.CharField(
        'Preferred Locale - SI or EN',
        max_length=5,
        blank=True
    )

    is_staff = models.BooleanField(
        'staff status',
        default=False,
    )

    is_active = models.BooleanField(
        'active',
        default=True,
    )

    date_joined = models.DateTimeField(
        'date joined',
        default=timezone.now,
    )

    USERNAME_FIELD = 'regNo'
    REQUIRED_FIELDS = ['userType', 'name']

    objects = UserManager()

    def __str__(self) -> str:
        return self.name

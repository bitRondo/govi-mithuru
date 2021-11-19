from django.db import models

class Claim(models.Model):
    claimID = models.CharField(
        'Claim ID',
        max_length=150,
        unique=True,
    )

    STATE_CHOICES = [
        (None, '- Select -'),
        (0, 'SUBMISSION_PENDING'),
        (1, 'SUBMITTED'),
        (2, 'IN_REVIEW'),
        (3, 'RESUBMISSION_REQUESTED'),
        (4, 'ACCEPTED'),
        (5, 'REJECTED'),
    ]

    state = models.SmallIntegerField(
        'State of the progress',
        choices=STATE_CHOICES,
        default=0,
    )

    # 1
    topic = models.CharField(
        'Topic of the claim',
        max_length=400,
    )

    # 2
    agriServiceCenter = models.CharField(
        'Agriculture Service Center of the farmer',
        max_length=150,
    )

    # 3
    farmerRegNo = models.CharField(
        'Farmer Registration number',
        max_length=200,
    )

    # 4
    farmerName = models.CharField(
        'Farmer name',
        max_length=250,
    )

    # 5
    farmerPhone = models.CharField(
        'Farmer phone',
        max_length=15,
    )

    # 6
    farmerNIC = models.CharField(
        'Farmer NIC',
        max_length=25,
    )

    # 7
    landRegNum = models.CharField(
        'Land Registration number',
        max_length=150,
    )

    # 8
    landArea = models.FloatField(
        'Land Area'
    )

    # 9
    crop = models.CharField(
        'Crop',
        max_length=150,
    )

    # 10
    cultivatedArea = models.FloatField(
        'Cultivated area'
    )

    # 11
    damageDate = models.DateField(
        'Date of damage'
    )

    DAMAGE_CAUSE_CHOICES = [
        (None, '- Select -'),
        (0, 'FLOOD'),
        (1, 'DROUGHT'),
        (2, 'ELEPHANTS'),
        (3,'FIRE'),
        (4, 'DISEASES_PESTS'),
        (5, 'OTHER'),
    ]

    # 12
    damageCause = models.SmallIntegerField(
        'Cause of damage',
        choices=DAMAGE_CAUSE_CHOICES,
    )

    # 13
    otherCause = models.CharField(
        'Other cause',
        max_length=200,
        blank=True
    )

    DAMAGE_LEVEL_CHOICES = [
        (None, '- Select -'),
        (0, 'COMPLETE_DAMAGE'),
        (1, 'PARTIAL_DAMAGE'),
        (2, 'MINOR_DAMAGE')
    ]

    # 14
    damageLevel = models.SmallIntegerField(
        'Damage Level',
        choices=DAMAGE_LEVEL_CHOICES,
    )

    # 15
    damageArea = models.FloatField(
        'Area of damage'
    )

    # 16
    compensation = models.FloatField(
        'Amount Requested'
    )

    # 17
    bankAccountNo = models.CharField(
        'Bank Account Number',
        max_length=150
    )

    # 18
    bank = models.CharField(
        'Bank name',
        max_length=150
    )

    # 19
    branch = models.CharField(
        'Bank branch',
        max_length=150
    )

    # 20
    gramaNiladhariDiv = models.CharField(
        'Grama Niladhari Division of the farmer',
        max_length=150,
        blank=True,
    )

    # 21
    address = models.CharField(
        'Address of the farmer',
        max_length=250,
        blank=True,
    )

    # 22
    landName = models.CharField(
        'Name of the land',
        max_length=150,
        blank=True,
    )

    # 23
    timeToHarvest = models.FloatField(
        'Tim to yield harvest',
        null=True,
    )

    # 24
    cultivatedDate = models.DateField(
        'Cultivated Date',
        null=True,
    )

    acceptedFields = models.CharField(
        'A comma-separated list of indexed fields',
        max_length=100,
        blank=True,
    )

    damageLevelComment = models.CharField(
        'Comment of Officer on damage level',
        max_length=250,
        blank=True,

    )

    compensationComment = models.CharField(
        'Comment of Officer on amount requested',
        max_length=250,
        blank=True,
    )

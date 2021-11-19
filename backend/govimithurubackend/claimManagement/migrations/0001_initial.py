# Generated by Django 3.2.9 on 2021-11-19 16:48

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Claim',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('claimID', models.CharField(max_length=150, unique=True, verbose_name='Claim ID')),
                ('state', models.SmallIntegerField(choices=[(None, '- Select -'), (0, 'SUBMISSION_PENDING'), (1, 'SUBMITTED'), (2, 'IN_REVIEW'), (3, 'RESUBMISSION_REQUESTED'), (4, 'ACCEPTED'), (5, 'REJECTED')], default=0, verbose_name='State of the progress')),
                ('topic', models.CharField(max_length=400, verbose_name='Topic of the claim')),
                ('agriServiceCenter', models.CharField(max_length=150, verbose_name='Agriculture Service Center of the farmer')),
                ('farmerRegNo', models.CharField(max_length=200, verbose_name='Farmer Registration number')),
                ('farmerName', models.CharField(max_length=250, verbose_name='Farmer name')),
                ('farmerPhone', models.CharField(max_length=15, verbose_name='Farmer phone')),
                ('farmerNIC', models.CharField(max_length=25, verbose_name='Farmer NIC')),
                ('landRegNum', models.CharField(max_length=150, verbose_name='Land Registration number')),
                ('landArea', models.FloatField(verbose_name='Land Area')),
                ('crop', models.CharField(max_length=150, verbose_name='Crop')),
                ('cultivatedArea', models.FloatField(verbose_name='Cultivated area')),
                ('damageDate', models.DateField(verbose_name='Date of damage')),
                ('damageCause', models.SmallIntegerField(choices=[(None, '- Select -'), (0, 'FLOOD'), (1, 'DROUGHT'), (2, 'ELEPHANTS'), (3, 'FIRE'), (4, 'DISEASES_PESTS'), (5, 'OTHER')], verbose_name='Cause of damage')),
                ('otherCause', models.CharField(blank=True, max_length=200, verbose_name='Other cause')),
                ('damageLevel', models.SmallIntegerField(choices=[(None, '- Select -'), (0, 'COMPLETE_DAMAGE'), (1, 'PARTIAL_DAMAGE'), (2, 'MINOR_DAMAGE')], verbose_name='Damage Level')),
                ('damageArea', models.FloatField(verbose_name='Area of damage')),
                ('compensation', models.FloatField(verbose_name='Amount Requested')),
                ('bankAccountNo', models.CharField(max_length=150, verbose_name='Bank Account Number')),
                ('bank', models.CharField(max_length=150, verbose_name='Bank name')),
                ('branch', models.CharField(max_length=150, verbose_name='Bank branch')),
                ('gramaNiladhariDiv', models.CharField(blank=True, max_length=150, verbose_name='Grama Niladhari Division of the farmer')),
                ('address', models.CharField(blank=True, max_length=250, verbose_name='Address of the farmer')),
                ('landName', models.CharField(blank=True, max_length=150, verbose_name='Name of the land')),
                ('timeToHarvest', models.FloatField(null=True, verbose_name='Tim to yield harvest')),
                ('cultivatedDate', models.DateField(null=True, verbose_name='Cultivated Date')),
                ('acceptedFields', models.CharField(blank=True, max_length=100, verbose_name='A comma-separated list of indexed fields')),
                ('damageLevelComment', models.CharField(blank=True, max_length=250, verbose_name='Comment of Officer on damage level')),
                ('compensationComment', models.CharField(blank=True, max_length=250, verbose_name='Comment of Officer on amount requested')),
            ],
        ),
    ]

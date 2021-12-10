# Generated by Django 3.2.9 on 2021-12-10 10:01

import claimManagement.models
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('claimManagement', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='Evidence',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('evidenceID', models.CharField(help_text='Required', max_length=250, verbose_name='Evidence ID')),
                ('date', models.DateField(verbose_name='Date taken')),
                ('latitude', models.FloatField(verbose_name='Latitude')),
                ('longitude', models.FloatField(verbose_name='Longitude')),
                ('description', models.CharField(blank=True, max_length=500, verbose_name='Evidence Description')),
                ('image', models.ImageField(upload_to=claimManagement.models.getImageFilePath, verbose_name='Evidence Image')),
            ],
        ),
    ]
# Generated by Django 3.2.4 on 2021-12-10 10:48

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('claimManagement', '0002_evidence'),
    ]

    operations = [
        migrations.AlterField(
            model_name='evidence',
            name='evidenceID',
            field=models.CharField(help_text='Required', max_length=250, unique=True, verbose_name='Evidence ID'),
        ),
    ]

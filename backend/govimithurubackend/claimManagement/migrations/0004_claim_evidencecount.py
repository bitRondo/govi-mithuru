# Generated by Django 3.2.4 on 2021-12-10 13:12

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('claimManagement', '0003_alter_evidence_evidenceid'),
    ]

    operations = [
        migrations.AddField(
            model_name='claim',
            name='evidenceCount',
            field=models.IntegerField(default=1, verbose_name='Number of evidences in this claim'),
        ),
    ]

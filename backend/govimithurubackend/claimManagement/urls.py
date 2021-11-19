from django.urls import path

from rest_framework.generics import ListCreateAPIView

from claimManagement.models import Claim
from claimManagement.serializers import ClaimSerializer

urlpatterns = [
    path('', ListCreateAPIView.as_view(queryset=Claim.objects.all(), serializer_class=ClaimSerializer), name='claim_list'),
]

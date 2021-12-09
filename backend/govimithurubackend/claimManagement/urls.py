from django.urls import path

from .views import ClaimListCreate, ClaimRetrieveUpdateDelete

urlpatterns = [
    path('', ClaimListCreate.as_view(), name='claim_list'),
    path('<str:id>', ClaimRetrieveUpdateDelete.as_view(), name='claim_object'),
]

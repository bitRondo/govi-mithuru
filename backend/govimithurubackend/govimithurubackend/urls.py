"""govimithurubackend URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, include

from django.conf import settings
from django.conf.urls.static import static

from accountManagement.views import index
from claimManagement.views import *

urlpatterns = [
    path('', index, name='index_view'),
    path('admin/', admin.site.urls),
    # path('users/', include('accountManagement.urls')),
    path('claims/', ClaimListCreate.as_view(), name='claim_list'),
    path('claims/<str:id>', ClaimRetrieveUpdateDelete.as_view(), name='claim_object'),
    path('evidences/', EvidenceModelViewSet.as_view({'get':'list', 'post':'create'}), name='evidences'),
    path('evidences/<str:id>', EvidenceModelViewSet.as_view({'get':'retrieve', 'delete':'destroy'}), name='evidence_object'),
]

if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
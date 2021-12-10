from django.http.response import Http404
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework import status
from rest_framework.viewsets import ModelViewSet
from django.shortcuts import get_object_or_404

from claimManagement.models import Claim, Evidence
from claimManagement.serializers import ClaimSerializer, EvidenceSerializer

class ClaimListCreate(APIView):
    """
    List all Claims, or create new Claim
    """

    def get(self, request, format=None):
        claims = Claim.objects.all()
        serializer = ClaimSerializer(claims, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        serializer = ClaimSerializer(data=request.data)
        
        if serializer.is_valid():
            serializer.save(state=1)
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class ClaimRetrieveUpdateDelete(APIView):
    """
    Retrieve, or Update a given Claim
    """

    def get_object(self, claimID):
        try:
            return Claim.objects.get(claimID=claimID)
        except Claim.DoesNotExist:
            raise Http404

    def get(self, request, id, format=None):
        claim = self.get_object(id)
        serializer = ClaimSerializer(claim)
        return Response(serializer.data)

    def put(self, request, id, format=None):
        claim = self.get_object(id)
        serializer = ClaimSerializer(claim, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, id, format=None):
        claim = self.get_object(id)
        claim.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)

class EvidenceModelViewSet(ModelViewSet):
    queryset = Evidence.objects.all()
    serializer_class = EvidenceSerializer

    def get_object(self, evidenceID):
        try:
            return Evidence.objects.get(evidenceID=evidenceID)
        except Evidence.DoesNotExist:
            raise Http404

    def retrieve(self, request, id):
        queryset = Evidence.objects.all()
        evidence = get_object_or_404(queryset, evidenceID=id)
        serializer = EvidenceSerializer(evidence)
        return Response(serializer.data)

    def destroy(self, request, id):
        evidence = self.get_object(id)
        evidence.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
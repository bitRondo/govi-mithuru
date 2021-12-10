from django.http.response import Http404, HttpResponse
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework import status
from rest_framework.generics import ListCreateAPIView

from accountManagement.models import User
from accountManagement.serializers import UserSerializer

def index(request):
    return HttpResponse("Welcome to GoviMithuru Backend!")

class UserListCreate(ListCreateAPIView):
    queryset = User.objects.all()
    serializer_class = UserSerializer

class UserRetrieveUpdateDelete(APIView):
    """
    Retrieve, or Update a given User
    """

    def get_object(self, nic):
        try:
            return User.objects.get(nic=nic)
        except User.DoesNotExist:
            raise Http404

    def get(self, request, nic, format=None):
        User = self.get_object(nic)
        serializer = UserSerializer(User)
        return Response(serializer.data)

    def put(self, request, nic, format=None):
        User = self.get_object(nic)
        serializer = UserSerializer(User, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, nic, format=None):
        User = self.get_object(nic)
        User.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)

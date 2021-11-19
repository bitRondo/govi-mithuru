from django.contrib.auth.base_user import BaseUserManager

class UserManager(BaseUserManager):
    def create_user(self, regNo, userType, name, password, **extra_fields):
        user = self.model(regNo=regNo, userType=userType, name=name, **extra_fields)
        user.set_password(password)
        user.save()
        return user

    def create_superuser(self, regNo, userType, name, password, **extra_fields):
        extra_fields.setdefault('is_superuser', True)
        extra_fields.setdefault('is_staff', True)

        return self.create_user(regNo, userType, name, password, **extra_fields)
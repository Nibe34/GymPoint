# Create certs directory if it doesn't exist
New-Item -ItemType Directory -Force -Path "src/main/resources/certs"

# Generate private key
openssl genrsa -out src/main/resources/certs/private.pem 2048

# Generate public key
openssl rsa -in src/main/resources/certs/private.pem -pubout -out src/main/resources/certs/public.pem

Write-Host "RSA key pair generated successfully!" 
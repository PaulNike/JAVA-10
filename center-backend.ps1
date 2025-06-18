# Levantar Config Server
Start-Process powershell -ArgumentList "Write-Host 'Levantando Config Server...'; java -jar 'D:\TECSUP\G10\clase\config-server\target\config-server-0.0.1-SNAPSHOT.jar'; Read-Host 'Presiona ENTER para cerrar Config Server'" -WindowStyle Normal

# Levantar Eureka Server
Start-Process powershell -ArgumentList "Write-Host 'Levantando Eureka Server...'; java -jar 'D:\TECSUP\G10\clase\eureka-server\target\eureka-server-0.0.1-SNAPSHOT.jar'; Read-Host 'Presiona ENTER para cerrar Eureka Server'" -WindowStyle Normal

#  Levantar Vault en modo dev
Start-Process powershell -ArgumentList "-NoExit", "-Command", "vault server -dev -dev-root-token-id='00000000-0000-0000-0000-000000000000'"

Start-Sleep -Seconds 5


#  Insertar secretos para ms-seguridad
vault kv put secret/ms-seguridad `
    key.signature="gqcvLHvj8f7Mc3BtKwXcTNjZP/z/OYSXZoF4OYgS+/RwsFrHwN5JOgxS4QHfQ1qaWVJFum4OnZsKmlZ9lptmhg==" `
    spring.datasource.url="jdbc:postgresql://localhost:5432/bdsecurityg10" `
    spring.datasource.username="postgres" `
    spring.datasource.password="admin"
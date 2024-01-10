# Calculadora remota simple UDP

Este repositorio contiene el código de una calculadora básica remota implementada en Java utilizando el protocolo UDP. La calculadora permite realizar operaciones simples (suma, resta, multiplicación y división) entre dos números. El servidor y el cliente se comunican a través de datagramas UDP para llevar a cabo las operaciones.

## Funcionalidades:

- **Servidor (`udpser`)**:
  - Escucha en un puerto específico para recibir datagramas UDP.
  - Procesa las operaciones recibidas y devuelve el resultado al cliente.
  - Admite operaciones de suma, resta, multiplicación y división.
  - Incorpora un "secreto" que se suma al resultado final.

- **Cliente (`udpcli`)**:
  - Envia una operación junto con dos números al servidor a través de datagramas UDP.
  - Espera la respuesta del servidor durante 10 segundos.
  - Muestra el resultado de la operación recibida del servidor.

## Estructura:

El proyecto consta de dos archivos fuente:

- udpser.java: Contiene la implementación del servidor.
- udpcli.java: Contiene la implementación del cliente.

Ambos archivos comparten funcionalidades relacionadas con la manipulación de datos y la comunicación UDP.

## Instrucciones de uso:

### Servidor (`udpser`)

1. Compile el archivo `udpser.java`.
   
```bash
javac udpser.java
```

2. Ejecute el servidor proporcionando el número de puerto como argumento.
   
```bash
java udpser <puerto>
```
Ejemplo: `java udpser 9123 99`

### Cliente (`udpcli`)

1. Compile el archivo `udpcli.java`.
```bash
javac udpcli.java
```

2. Ejecute el cliente proporcionando la dirección IP del servidor y el número de puerto como argumentos.

  ```bash
  java udpcli <ip_servidor> <puerto_servidor> <numero1> <numero2> '<operación>'
  ```
Ejemplo: `java udpcli 127.0.0.1 9123 33 34 '*'`

## Notas:
- El servidor y el cliente deben ejecutarse en máquinas diferentes si no se utiliza 'localhost'.
- Asegúrese de proporcionar los argumentos correctos al ejecutar tanto el servidor como el cliente.
- La operación debe estar rodeada por comillas simples (' ') para evitar problemas con la interpretación de argumentos.

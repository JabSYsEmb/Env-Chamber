FROM maven

WORKDIR /home/app/envChamber

COPY . .

CMD mvn clean package

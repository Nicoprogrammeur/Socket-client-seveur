# Définition d'un serveur réseau rudimentaire
# Ce serveur attend la connexion d'un client, pour entamer un dialogue avec lui

import socket, sys

HOST = '127.0.0.1'
PORT = 5000

# 1) création du socket :
mySocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# 2) liaison du socket à une adresse précise :
try:
    mySocket.bind((HOST, PORT))
except socket.error:
    print("La liaison du socket à l'adresse choisie a échoué.")
    sys.exit()

while 1:
    # 3) Attente de la requête de connexion d'un client :
    print("Serveur prêt, en attente de requêtes ...")
    mySocket.listen(5)
   
    # 4) Etablissement de la connexion :
    connexion, adresse = mySocket.accept()
    print("Client connecté, adresse IP %s, port %s" % (adresse[0], adresse[1]))
   
    # 5) Dialogue avec le client :
    connexion.send(b"Vous etes connecte au serveur Nicoprog. Envoyez vos messages.")
    msgClient = connexion.recv(1024).decode()
    while 1:
        print("C>", msgClient)
        if msgClient.upper() == "FIN" or msgClient =="":
            break
        msgServeur = input("S> ")
        connexion.send(msgServeur.encode())
        msgClient = connexion.recv(1024).decode()

    # 6) Fermeture de la connexion :
    connexion.send(b"Au revoir")
    print("Connexion interrompue.")
    connexion.close()

    ch = input("<R>ecommencer <T>erminer ? ")
    if ch.upper() =='T':
        break
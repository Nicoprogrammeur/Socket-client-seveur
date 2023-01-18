import * as socket from 'socket';
import * as sys from 'sys';
import * as threading from 'threading';
var connexion, host, port, th_E, th_R;
host = "127.0.0.1";
port = 40000;

class ThreadReception extends threading.Thread {
  /* objet thread gérant la réception des messages */
  constructor(conn) {
    threading.Thread.__init__(this);

    this.connexion = conn;
  }

  run() {
    var message_recu;

    while (1) {
      message_recu = this.connexion.recv(1024).decode();
      console.log("*" + message_recu + "*");

      if (message_recu === "" || message_recu.upper() === "FIN") {
        break;
      }
    }

    th_E._Thread__stop();

    console.log("Client arr\u00eat\u00e9. Connexion interrompue.");
    this.connexion.close();
  }

}

class ThreadEmission extends threading.Thread {
  /* objet thread gérant l'émission des messages */
  constructor(conn) {
    threading.Thread.__init__(this);

    this.connexion = conn;
  }

  run() {
    var message_emis;

    while (1) {
      message_emis = input();
      this.connexion.send(message_emis.encode());
    }
  }

}

connexion = socket.socket(socket.AF_INET, socket.SOCK_STREAM);

try {
  connexion.connect([host, port]);
} catch (e) {
  if (e instanceof socket.error) {
    console.log("La connexion a \u00e9chou\u00e9.");
    sys.exit();
  } else {
    throw e;
  }
}

console.log("Connexion \u00e9tablie avec le serveur.");
th_E = new ThreadEmission(connexion);
th_R = new ThreadReception(connexion);
th_E.start();
th_R.start();
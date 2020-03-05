//Declaracion de un objeto para crear el juego
var juego = new Phaser.Game(580, 580, Phaser.CANVAS, "mapa");

//Declaracion de todas las variables que usaremos en el mapa
var cursor
var teclas
var parque
var entrada
var oficina
var quedrada
var plazaReloj
var plazoletaSuba
var plazoletaBotanica
var plazoletaCentral
var obeliscoInvierno
var obeliscoEquinoccio
var obeliscoVerano
var imgParque
var imgEntrada
var imgPlazaReloj
var imgPlazoletaSuba
var imgPlazoletaBotanica
var imgPlazoletaCentral
var imgObeliscoInvierno
var imgObeliscoEquinoccio
var imgObeliscoVerano

var mapaInteractivo = {
	//Cargamos todas las imagenes que se usaran en el mapa
	preload: function() {
		juego.load.image("fondo","Imagenes/MapaInteractivo.jpg");
		juego.load.image("cursor","Imagenes/star.png");
		juego.load.image("parque","Imagenes/park.png");
		juego.load.image("reloj","Imagenes/clock.png");
		juego.load.image("entrada","Imagenes/entrance.png");
		juego.load.image("oficina","Imagenes/buildings.png");
		juego.load.image("quedrada","Imagenes/waterfall.png");
		juego.load.image("plazoleta","Imagenes/flagMarker.png");
		juego.load.image("obelisco","Imagenes/mapMarker.png");
		juego.load.image("imgParque","Imagenes/Foto10.jpg");
		juego.load.image("imgPlazaReloj","Imagenes/Foto2.jpg");
		juego.load.image("imgEntrada","Imagenes/Foto1.jpg");
		juego.load.image("imgPlazoletaSuba","Imagenes/Foto12.jpg");
		juego.load.image("imgPlazoletaBotanica","Imagenes/Principal.jpg");
		juego.load.image("imgPlazoletaCentral","Imagenes/Foto3.jpg");
		juego.load.image("imgObelisco","Imagenes/Foto4.jpg");
	},

	create: function() {
		//Agregamos el mapa de fondo y sus dimensiones
		juego.add.tileSprite(0, 0, 1130, 770, "fondo");
		juego.world.setBounds(0, 0, 1130, 770);
		//Agregamos las fisicas al mapa 
		juego.physics.startSystem(Phaser.Physics.P2JS);
		//Agregamos el cursor en el centro del mapa
		cursor = juego.add.sprite(juego.world.centerX, juego.world.centerY, "cursor");
		//Habilitamos las fisicas en el cursor
		juego.physics.p2.enable(cursor);
		//Inicialicion de la deteccion de teclado para el movimiento
		teclas = juego.input.keyboard.createCursorKeys();
		//Configuramos que la camara siga al cursor
		juego.camera.follow(cursor);
		//Agregamos los iconos al mapa
		parque = juego.add.sprite(480, 580, "parque");
		entrada = juego.add.sprite(80, 350, "entrada");
		oficina = juego.add.sprite(380, 430, "oficina");
		quedrada = juego.add.sprite(750, 250, "quedrada");
		plazaReloj = juego.add.sprite(500, 420, "reloj");
		plazoletaBotanica = juego.add.sprite(700, 670, "plazoleta");
		plazoletaCentral = juego.add.sprite(690, 500, "plazoleta");
		plazoletaSuba = juego.add.sprite(250, 100, "plazoleta");
		obeliscoEquinoccio = juego.add.sprite(1090, 500, "obelisco");
		obeliscoInvierno = juego.add.sprite(1000, 655, "obelisco");
		obeliscoVerano = juego.add.sprite(1070, 380, "obelisco");
		imgEntrada = juego.add.button(30, 80, "imgEntrada");
		imgParque = juego.add.button(200, 200, "imgParque");
		imgPlazaReloj = juego.add.button(250, 300, "imgPlazaReloj");
		//imgPlazoletaBotanica = juego.add.button(250, 300, "imgPlazoletaBotanica");
		//imgPlazoletaSuba = juego.add.button(250, 300, "imgPlazoletaSuba");
		//imgObeliscoEquinoccio = juego.add.button(350, 300, "imgObelisco");
		//imgObeliscoInvierno = juego.add.button(250, 300, "imgObelisco");
		//imgObeliscoVerano = juego.add.button(250, 300, "imgObelisco");
		
	},

	update: function(){

		if(cursor.position.y>330 && cursor.position.y<380 && cursor.position.x >70 && cursor.position.x<120){
			imgEntrada.visible = true;
		}else{
			imgEntrada.visible = false;
		}

		if(cursor.position.y>570 && cursor.position.y<620 && cursor.position.x >470 && cursor.position.x<520){
			imgParque.visible = true;
		}else{
			imgParque.visible = false;
		}

		if(cursor.position.y>430 && cursor.position.y<480 && cursor.position.x >520 && cursor.position.x<570){
			imgPlazaReloj.visible = true;
		}else{
			imgPlazaReloj.visible = false;
		}
		
		//variables encargadas de darle el movimiento al cursor en las distintas direcciones
		y=500;
		cursor.body.setZeroVelocity();
		if (teclas.up.isDown){
			cursor.body.moveUp(300)
		} else if (teclas.down.isDown){
			cursor.body.moveDown(300);
		}

		if (teclas.left.isDown){
			cursor.body.velocity.x = -300;
		} else if (teclas.right.isDown){
			cursor.body.moveRight(300);
		}
	}
};

juego.state.add("mapa", mapaInteractivo);
juego.state.start("mapa");

// Declaracion de un objeto Phaser para crear el mapa interactivo
var juego = new Phaser.Game(400, 350, Phaser.CANVAS, "mapa");

//Declaracion de todas las variables que seran utilizadas en el mapa
var picoAguila
var roquedal
var mariposas
var dosQuebradas
var robles
var cascada
var senderoColonial
var cursor
var teclas

var interactivo = {
	//Cargamos todas las imagenes ha utilizar en el mapa
	preload: function() {
		// Escala la imagen al reducir el tamaño de la ventana
		this.scale.scaleMode = Phaser.ScaleManager.SHOW_ALL;
		juego.load.image("fondo","Imgs/mapa_senderos.jpeg");
		juego.load.image("cursor","Imgs/cursor.png");
		juego.load.image("picoAguila","Imgs/picoAguila.jpg");
		juego.load.image("roquedal","Imgs/roquedal.jpg");
		juego.load.image("mariposas","Imgs/mariposas.jpg");
		juego.load.image("dosQuebradas","Imgs/dosquebradas.jpg");
		juego.load.image("robles","Imgs/robles.jpg");
		juego.load.image("cascada","Imgs/cascada.jpg");
		juego.load.image("senderoColonial","Imgs/senderoColonial.jpg");

	},

	create: function() {       
		//Imagen de fondo y sus dimensiones
		juego.add.tileSprite(0, 0, 950, 367, "fondo");
		juego.world.setBounds(0, 0, 950, 367);
		//Fisicas al mapa 
		juego.physics.startSystem(Phaser.Physics.P2JS);
		//Cursor en el centro del mapa
		cursor = juego.add.sprite(juego.world.centerX, juego.world.centerY, "cursor");
		//Habilitamos las fisicas en el cursor
		juego.physics.p2.enable(cursor);
		//Inicialicion de la deteccion de teclado para el movimiento
		teclas = juego.input.keyboard.createCursorKeys();
		//Configuracion para que la camara siga al cursor
		juego.camera.follow(cursor);
		//Puntos de referencia
		picoAguila = juego.add.button(600, 80, "picoAguila");
		roquedal = juego.add.button(600, 80, "roquedal");
		mariposas = juego.add.button(600, 80, "mariposas");
		dosQuebradas = juego.add.button(420, 120, "dosQuebradas");
		robles = juego.add.button(150, 90, "robles");
		cascada = juego.add.button(50, 80, "cascada");
		senderoColonial = juego.add.button(560, 50, "senderoColonial");
				
	},

	update: function(){

		//Muestra la imagen del Pico del Aguila
		if(cursor.position.y>200 && cursor.position.y<240 && cursor.position.x >870 && cursor.position.x<910){
			picoAguila.visible = true;
		}else{
			picoAguila.visible = false;
		}

		//Muestra la imagen del Roquedal
		if(cursor.position.y>210 && cursor.position.y<250 && cursor.position.x >730 && cursor.position.x<770){
			roquedal.visible = true;
		}else{
			roquedal.visible = false;
		}

		//Muestra la imagen de las Mariposas
		if(cursor.position.y>280 && cursor.position.y<320 && cursor.position.x >730 && cursor.position.x<770){
			mariposas.visible = true;
		}else{
			mariposas.visible = false;
		}

		//Muestra la imagen de Dos Quebradas
		if(cursor.position.y>310 && cursor.position.y<350 && cursor.position.x >550 && cursor.position.x<590){
			dosQuebradas.visible = true;
		}else{
			dosQuebradas.visible = false;
		}

		//Muestra la imagen del Bosque de Robles
		if(cursor.position.y>300 && cursor.position.y<340 && cursor.position.x >300 && cursor.position.x<340){
			robles.visible = true;
		}else{
			robles.visible = false;
		}
		
		//Muestra la imagen de la Cascada
		if(cursor.position.y>220 && cursor.position.y<260 && cursor.position.x >90 && cursor.position.x<130){
			cascada.visible = true;
		}else{
			cascada.visible = false;
		}

		//Muestra la imagen del Sendero Colonial
		if(cursor.position.y>70 && cursor.position.y<110 && cursor.position.x >710 && cursor.position.x<750){
			senderoColonial.visible = true;
		}else{
			senderoColonial.visible = false;
		}
		
		//variables encargadas del movimiento del cursor en las distintas direcciones
		y=150;
		cursor.body.setZeroVelocity();
		if (teclas.up.isDown){
			cursor.body.moveUp(250)
		} else if (teclas.down.isDown){
			cursor.body.moveDown(250);
		}

		if (teclas.left.isDown){
			cursor.body.velocity.x = -250;
		} else if (teclas.right.isDown){
			cursor.body.moveRight(250);
		}
		
	}
};

//Adicion del estado al mapa
juego.state.add("mapa", interactivo);
juego.state.start("mapa");

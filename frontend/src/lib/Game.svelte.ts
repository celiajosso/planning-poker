// import randomName from '@scaleway/random-name';
import { DeckContainer } from "./DeckContainer";
import { GameContainer } from "./GameContainer";
import { Application, Assets, Container, Texture } from "pixi.js";
import "@pixi/layout";
import { tw } from "@pixi/layout/tailwind";
import { WebSocketManager } from "./WebsocketManager";
import { StoryDisplay } from "./StoryDisplay";

class Storage {
	public user: UserDTO = $state({
		id: "",
		username: "",
		role: "",
		roomId: "",
		card: -1,
	});
	public room: RoomDTO = $state({
		id: "",
		name: "",
		stories: [],
		storySelected: null,
	});
}

export namespace Game {
	export let app: Application;
	let hasPlayed = false;

	export let storage = new Storage();

	export let textures: { [k: string]: Texture } = {};

	export let gameContainer: GameContainer;
	export let storyDisplay: StoryDisplay;
	export let deckContainer: DeckContainer;

	let player_cache: UserDTO[] = [];

	export function addPlayer(user: UserDTO) {
		if (gameContainer === undefined) {
			player_cache.push(user);
		} else {
			gameContainer.addPlayer(user);
		}
	}

	export function removePlayer(user: UserDTO) {
		gameContainer?.removePlayer(user);
	}

	export function updateShowStory() {
		gameContainer.alpha = 0
		storyDisplay.setStory(storage.room.storySelected)
		deckContainer.visible = storage.room.storySelected !== null
	}

	export async function init(canvas: HTMLCanvasElement) {
		app = new Application();

		// Initialize the application.
		await app.init({ resizeTo: window, canvas: canvas, background: "#e2e8f0" });

		app.stage.layout = { width: app.screen.width, height: app.screen.height };

		// Load assets

		let manifest = {
			bundles: [
				{
					name: "fonts",
					assets: [{ alias: "Righteous", src: "/Righteous-Regular.ttf" }],
				},
				{
					name: "textures",
					assets: [
						{
							alias: "Card",
							src: "/cards/Squircle.png",
							data: { parseAsGraphicsContext: true },
						},
					],
				},
			],
		};

		Assets.init({ manifest });

		await Assets.loadBundle("fonts");
		textures = await Assets.loadBundle("textures");

		// GlobalContainer

		const globalContainer = new Container({
			layout: tw`flex flex-col items-center justify-between w-full h-dvh m-8 mb-22`,
		});

		// GameContainer

		gameContainer = new GameContainer({
			layout: tw`flex flex-row items-center justify-center w-full gap-4 flex-wrap flex-1`,
		});

		// initialize the deck

		deckContainer = new DeckContainer(
			{
				layout: tw`flex flex-row items-center justify-center w-full gap-4 flex-wrap`,
			},
			validate,
		);

		const resizeObserver = new ResizeObserver(() => {
			app.stage.layout = {
				width: app.screen.width,
				height: app.screen.height,
			};
		});

		resizeObserver.observe(app.canvas);

		storyDisplay = new StoryDisplay()

		updateShowStory()

		globalContainer.addChild(storyDisplay);
		globalContainer.addChild(gameContainer);
		globalContainer.addChild(deckContainer);
		app.stage.addChild(globalContainer);

		for (let player of player_cache) {
			gameContainer.addPlayer(player);
		}

		player_cache = [];
	}

	export function restart() {
		if (storage.room.storySelected === null) return
		storage.user.card = -1;
		WebSocketManager.sendMessage("UserUpdate", storage.user, null, null);
		gameContainer.alpha = 0;
		deckContainer.visible = true;
	}

	export function createRoom(user: UserDTO, room: RoomDTO) {
		WebSocketManager.sendMessage("RoomCreate", user, room, null);
	}

	export function joinRoom(user: UserDTO) {
		WebSocketManager.sendMessage("RoomJoin", user, null, null);
	}

	export function updateRoom(room: RoomDTO) {
		WebSocketManager.sendMessage("RoomUpdate", null, room, null);
	}

	export function updateUser(user: UserDTO) {
		WebSocketManager.sendMessage("UserUpdate", user, null, null);
	}

	export function validate(selected: number) {
		if (storage.room.storySelected === null) return
		gameContainer.alpha = 1;
		deckContainer.visible = false;
		storage.user.card = selected;
		WebSocketManager.sendMessage("UserUpdate", storage.user, null, null);
	}

	export function quitRoom() {
		WebSocketManager.sendMessage("RoomQuit", null, null, null);
	}

	export function updatePlayer(user: UserDTO) {
		Game.gameContainer.updatePlayer(user)
	}

	export function createStory(event) {
		const formData = new FormData(event.target);
		const data = {};

		for (const [key, value] of formData.entries()) {
			data[key] = value;
		}

		WebSocketManager.sendMessage("StoryCreate", null, null, {
			id: "",
			title: data.title,
			description: data.description,
			finalEstimate: data.score,
			roomId: storage.room.id,
			votes:{},
			isSaved:false
		});
	}

	export function createStory2(title: string, description: string, score: number) {
		WebSocketManager.sendMessage("StoryCreate", null, null, {
			id: "",
			title: title,
			description: description,
			finalEstimate: score,
			roomId: storage.room.id,
			votes:{},
			isSaved:false
		});
	}

	export function updateStory(id, event) {
		const formData = new FormData(event.target);
		const data = {};

		for (const [key, value] of formData.entries()) {
			data[key] = value;
		}

		WebSocketManager.sendMessage("StoryUpdate", null, null, {
			id,
			title: data.title,
			description: data.description,
			finalEstimate: data.score,
			roomId: storage.room.id,
			votes:{},
			isSaved:false
		});
	}

	export function selectStory(id: string) {
		WebSocketManager.sendMessage("StorySelect", null, null, {
			id,
			title: "",
			description: "",
			finalEstimate: 0,
			roomId: storage.room.id,
			votes:{},
			isSaved:false
		});
	}

	export function saveStory() {
		WebSocketManager.sendMessage("StorySave", null, null, {
			id:"",
			title: "",
			description: "",
			finalEstimate: 0,
			roomId: storage.room.id,
			votes:{},
			isSaved:false
		});
	}

	export function newRoundStory() {
		WebSocketManager.sendMessage("StoryNewRound", null, null, {
			id:"",
			title: "",
			description: "",
			finalEstimate: 0,
			roomId: storage.room.id,
			votes:{},
			isSaved:false
		});
	}

	export function unselectStory() {
		WebSocketManager.sendMessage("StorySelect", null, null, {
			id: "",
			title: "",
			description: "",
			finalEstimate: 0,
			roomId: storage.room.id,
			votes:{},
			isSaved:false
		});
	}

	export function deleteStory(id: string) {
		WebSocketManager.sendMessage("StoryDelete", null, null, {
			id,
			title: "",
			description: "",
			finalEstimate: 0,
			roomId: storage.room.id,
			votes:{},
			isSaved:false
		});
	}
}

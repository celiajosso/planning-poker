import { Container, type ContainerChild, type ContainerOptions } from 'pixi.js';
import '@pixi/layout';
import { Player } from './Player';
import type { UserDTO } from './UserDTO';

export class GameContainer extends Container {
	players: { [id: string]: Player } = {};

	constructor(options: ContainerOptions<ContainerChild>) {
		super(options);
	}

	// hidden(bool: boolean) {
	// 	// this.blur.blur = bool ? 8 : 0;
	// }

	setSize(width: number, height: number) {
		this.width = width;
		this.height = height;
	}

	addPlayer(playerInfo: UserDTO): void {
		const p = new Player(playerInfo.username, playerInfo.card);
		this.players[playerInfo.id] = p;
		this.addChild(p);
	}

	removePlayer(playerInfo: UserDTO): void {
		this.removeChild(this.players[playerInfo.id]);
		delete this.players[playerInfo.id];
	}

	// updateHidden(bool: boolean): void {
	// 	this.hidden(bool);
	// }
}

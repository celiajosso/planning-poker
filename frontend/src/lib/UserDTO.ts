export class UserDTO {
	id: string = 'Dummy';
	username: string;
	role: string = 'Dummy';
	roomId: string;
	card: number = -1;

	constructor(username: string, roomId: string) {
		this.username = username;
		this.roomId = roomId;
	}

	update(userDTO:UserDTO){
		this.id = userDTO.id;
		this.username = userDTO.username;
		this.role = userDTO.role;
		this.roomId = userDTO.roomId;
		this.card = userDTO.card;
	}

}

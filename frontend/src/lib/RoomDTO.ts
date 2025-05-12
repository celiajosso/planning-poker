import type { StoryDTO } from "./StoryDTO";

export class RoomDTO {
	constructor(
		public id: string,
		public name: string,
		public storySelected: StoryDTO|null = null,
		public stories: StoryDTO[] = []
	) {}

	update(roomDTO:RoomDTO){
		this.id = roomDTO.id;
		this.name = roomDTO.name;
		this.storySelected = roomDTO.storySelected;
		this.stories = roomDTO.stories;
	}
}

import type { StoryDTO } from "./StoryDTO";

export class RoomDTO {
	constructor(
		public id: string,
		public name: string,
		public storySelected: StoryDTO|null = null,
		public stories: StoryDTO[] = []
	) {}
}

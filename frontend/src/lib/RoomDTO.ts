import type { StoryDTO } from "./StoryDTO";

export interface RoomDTO {
	id: string
	name: string
	storySelected: StoryDTO | null
	stories: StoryDTO[]
}

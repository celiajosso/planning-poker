<script lang="ts">
	import { onMount } from "svelte";
	import { Icon } from "@steeze-ui/svelte-icon";
	import {
		ArrowPath,
		ArrowRightEndOnRectangle,
		ChevronDoubleLeft,
		ArrowDownTray,
		ArrowUpTray,
		Plus,
	} from "@steeze-ui/heroicons";
	import { goto } from "$app/navigation";
	import { Game } from "$lib/Game.svelte";
	import { WebSocketManager } from "$lib/WebsocketManager";

	let canvas: HTMLCanvasElement;

	let loaded = $state(false);

	onMount(async () => {
		if (WebSocketManager.socket === undefined) {
			goto("/");
		} else {
			await Game.init(canvas);
			loaded = true;
		}
	});

	import * as Table from "$lib/components/ui/table/index.js";

	const issues = [
		{
			title: "SCRUM-1",
			description:
				"The User story related to SCRUM-1 (As a user, I want to be able to ... so that I can ...)",
			score: "1",
		},
		{
			title: "SCRUM-2",
			description:
				"The User story related to SCRUM-1 (As a user, I want to be able to ... so that I can ...)",
			score: "7",
		},
		{
			title: "SCRUM-3",
			description:
				"The User story related to SCRUM-1 (As a user, I want to be able to ... so that I can ...)",
			score: "11",
		},
	];

	import * as Sheet from "$lib/components/ui/sheet/index.js";
	import { Button } from "$lib/components/ui/button/index.js";
	import { Input } from "$lib/components/ui/input/index.js";
	import { Label } from "$lib/components/ui/label/index.js";
</script>

<canvas bind:this={canvas}></canvas>
{#if loaded}
	<Sheet.Root>
		<Sheet.Trigger asChild let:builder>
			<Button
				builders={[builder]}
				variant=""
				class="absolute top-4 right-4 z-50"
			>
				<Icon
					class="color-gray-900 size-8"
					src={ChevronDoubleLeft}
					theme="solid"
				/>
			</Button>
		</Sheet.Trigger>
		<Sheet.Content side="right" class="bg-white">
			<Sheet.Header class="flex flex-row justify-between items-center">
				<div>
					<Sheet.Title>Issues</Sheet.Title>
					<Sheet.Description>
						Make changes to your User Stories.
					</Sheet.Description>
				</div>
				<div>
					<Button builders={[]}>
						<Icon
							class="color-gray-900 size-6"
							src={ArrowDownTray}
							theme="solid"
						/>
					</Button>
					<Button builders={[]}>
						<Icon
							class="color-gray-900 size-6"
							src={ArrowUpTray}
							theme="solid"
						/>
					</Button>
				</div>
			</Sheet.Header>
			<div class="grid gap-4 py-4">
				<Table.Root>
					<Table.Header>
						<Table.Row>
							<Table.Head>CB</Table.Head>
							<Table.Head>Title</Table.Head>
							<Table.Head>Description</Table.Head>
							<Table.Head>Score</Table.Head>
							<Table.Head>I</Table.Head>
						</Table.Row>
					</Table.Header>
					<Table.Body>
						{#each issues as issue, i (i)}
							<Table.Row>
								<Table.Cell>CB</Table.Cell>
								<Table.Cell>{issue.title}</Table.Cell>
								<Table.Cell>{issue.description}</Table.Cell>
								<Table.Cell>{issue.score}</Table.Cell>
								<Table.Cell>I</Table.Cell>
							</Table.Row>
						{/each}
					</Table.Body>
				</Table.Root>
			</div>
			<Sheet.Footer>
				<Button builders={[]} class="outline items-center">
					<Icon
						class="color-gray-900 size-6"
						src={Plus}
						theme="solid"
					/>
					Add an issue
				</Button>
				<Sheet.Close asChild let:builder></Sheet.Close>
			</Sheet.Footer>
		</Sheet.Content>
	</Sheet.Root>
	<div
		class="absolute left-0 bottom-4 flex justify-center items-center w-full"
	>
		<div class="bg-white rounded-xl flex px-4 py-2 gap-4 flex-col">
			<input bind:value={Game.user.username} type="text" />
			<input bind:value={Game.room.id} type="text" />
			<div class="flex items-center justify-center">
				<button onclick={() => Game.restart()}>
					<Icon
						class="color-gray-900 size-8"
						src={ArrowPath}
						theme="solid"
					/>
				</button>
				<button onclick={() => Game.quitRoom()}>
					<Icon
						class="color-gray-900 size-8"
						src={ArrowRightEndOnRectangle}
						theme="solid"
					/>
				</button>
			</div>
		</div>
	</div>
{/if}

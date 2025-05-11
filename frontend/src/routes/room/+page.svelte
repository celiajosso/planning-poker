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
		Trash,
		PaperAirplane,
		PencilSquare,
	} from "@steeze-ui/heroicons";
	import { goto } from "$app/navigation";
	import { Game } from "$lib/Game.svelte";
	import { WebSocketManager } from "$lib/WebsocketManager";
	import * as Sheet from "$lib/components/ui/sheet/index.js";
	import { Button } from "$lib/components/ui/button/index.js";
	import { Input } from "$lib/components/ui/input/index.js";
	import { Label } from "$lib/components/ui/label/index.js";

	import { Checkbox } from "$lib/components/ui/checkbox/index.js";

	import * as Tooltip from "$lib/components/ui/tooltip/index.js";

	import * as Tabs from "$lib/components/ui/tabs/index.js";
	import ButtonIcon from "$lib/ButtonIcon.svelte";

	let checked = false;

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
			description: "The User story ...",
			score: "1",
			selectedScore: "1",
		},
		{
			title: "SCRUM-2",
			description: "The User story ...",
			score: "7",
			selectedScore: "7",
		},
		{
			title: "SCRUM-3",
			description: "The User story ...",
			score: "11",
			selectedScore: "11",
		},
	];

	let fileInput;

	function handleButtonClick() {
		fileInput.click();
	}

	function handleFileChange(event) {
		const file = event.target.files[0];
		if (file && file.type === "text/csv") {
			console.log("CSV file selected:", file);
		} else {
			alert("Please selectt a CSV file.");
		}
	}

	// /!\ A CHANGER SELON LE FORMAT CSV DE JIRA !!!
	function exportToCSV() {
		const csvRows = [];

		const headers = ["Title", "Description", "Score"];
		csvRows.push(headers.join(","));

		for (const issue of issues) {
			const row = [issue.title, issue.description, issue.score];
			csvRows.push(
				row.map((value) => `"${value.replace(/"/g, '""')}"`).join(","),
			);
		}

		const csvContent = csvRows.join("\n");

		const blob = new Blob([csvContent], {
			type: "text/csv;charset=utf-8;",
		});
		const url = URL.createObjectURL(blob);
		const link = document.createElement("a");
		link.setAttribute("href", url);
		link.setAttribute("download", "issues_export.csv");
		document.body.appendChild(link);
		link.click();
		document.body.removeChild(link);
		URL.revokeObjectURL(url);
	}

	import * as Select from "$lib/components/ui/select/index.js";

	const scores = Array.from({ length: 14 }, (_, i) => ({
		value: i.toString(),
		label: i.toString(),
	}));

	let value = $state("");

	import { ScrollArea } from "$lib/components/ui/scroll-area/index.js";
</script>

<canvas bind:this={canvas}></canvas>
{#if loaded}
	<Sheet.Root>
		<Sheet.Trigger class="absolute top-5 right-5">
			<ButtonIcon>
				<Icon
					class="color-gray-800 size-6"
					src={ChevronDoubleLeft}
					theme="solid"
				/>
			</ButtonIcon>
		</Sheet.Trigger>
		<Sheet.Content side="right" class="bg-[#f4f4f9] text-gray-900">
			<Tabs.Root value="issues" class="w-full">
				<Tabs.List
					class=" w-full flex bg-gray-200 mt-5 justify-between"
				>
					<Tabs.Trigger
						class="font-semibold  bg-white mr-1 flex-1"
						value="issues">Issues</Tabs.Trigger
					>
					<Tabs.Trigger
						class="font-semibold  bg-white mr-1 flex-1"
						value="profile">Profile</Tabs.Trigger
					>
					<Tabs.Trigger
						class="font-semibold  bg-white flex-1"
						value="room-settings">Room Settings</Tabs.Trigger
					>
				</Tabs.List>
				<Tabs.Content value="issues">
					<div class="grid gap-2 p-5 my-5 border rounded-lg">
						<div class="flex flex-row justify-between items-center">
							<div>
								<p class="font-bold text-xl">Issues</p>
								<p class="text-sm">
									Make changes to your User Stories.
								</p>
							</div>
							<div
								class="flex flex-row items-center h-full gap-2"
							>
								<Tooltip.Root>
									<Tooltip.Trigger>
										<ButtonIcon onclick={handleButtonClick}>
											<Icon
												class="color-gray-800 size-5"
												src={ArrowDownTray}
												theme="solid"
											/>
										</ButtonIcon>
									</Tooltip.Trigger>
									<Tooltip.Content>
										<p>Import CSV file</p>
									</Tooltip.Content>
								</Tooltip.Root>
								<input
									bind:this={fileInput}
									type="file"
									accept=".csv"
									onchange={handleFileChange}
									style="display: none;"
								/>
								<Tooltip.Root>
									<Tooltip.Trigger>
										<ButtonIcon onclick={exportToCSV}>
											<Icon
												class="color-gray-800 size-5"
												src={ArrowUpTray}
												theme="solid"
											/>
										</ButtonIcon>
									</Tooltip.Trigger>
									<Tooltip.Content>
										<p>Export CSV file</p>
									</Tooltip.Content>
								</Tooltip.Root>
							</div>
						</div>

						<Table.Root>
							<Table.Header>
								<Table.Row>
									<Table.Head class="text-center">
										<Checkbox
											id="terms"
											bind:checked
											aria-labelledby="terms-label"
											class="mx-auto"
										/>
									</Table.Head>

									<Table.Head>Title</Table.Head>
									<Table.Head>Description</Table.Head>
									<Table.Head>Score</Table.Head>
									<Table.Head></Table.Head>
								</Table.Row>
							</Table.Header>
							<Table.Body>
								{#each issues as issue, i (i)}
									<Table.Row>
										<Table.Cell
											><Checkbox
												id="terms"
												bind:checked
												aria-labelledby="terms-label"
											/></Table.Cell
										>
										<Table.Cell>{issue.title}</Table.Cell>
										<Table.Cell>
											<p class="truncate max-w-96">
												{issue.description}
											</p></Table.Cell
										>
										<Table.Cell>
											<!-- {issue.score} -->
											<Select.Root>
												<Select.Trigger
													class="w-[100px]"
												>
													<Select.Value
														placeholder="Score"
													/>
												</Select.Trigger>
												<Select.Content>
													<Select.Group>
														<ScrollArea
															class="h-20"
														>
															{#each scores as score}
																<Select.Item
																	value={score.value}
																	label={score.value}
																	>{score.value}</Select.Item
																>
															{/each}
														</ScrollArea>
													</Select.Group>
												</Select.Content>
												<Select.Input name="score" />
											</Select.Root>
										</Table.Cell>
										<Table.Cell>
											<div
												class="flex flex-row items-center h-full gap-2"
											>
												<Tooltip.Root>
													<Tooltip.Trigger>
														<ButtonIcon>
															<Icon
																class="color-gray-800 size-5"
																src={Trash}
																theme="outline"
															/>
														</ButtonIcon>
													</Tooltip.Trigger>
													<Tooltip.Content>
														<p>Delete</p>
													</Tooltip.Content>
												</Tooltip.Root>
												<Tooltip.Root>
													<Tooltip.Trigger>
														<ButtonIcon>
															<Icon
																class="color-gray-800 size-5"
																src={PencilSquare}
																theme="outline"
															/>
														</ButtonIcon>
													</Tooltip.Trigger>
													<Tooltip.Content>
														<p>Modify</p>
													</Tooltip.Content>
												</Tooltip.Root>
												<Tooltip.Root>
													<Tooltip.Trigger>
														<ButtonIcon>
															<Icon
																class="color-gray-800 size-5"
																src={PaperAirplane}
																theme="outline"
															/>
														</ButtonIcon>
													</Tooltip.Trigger>
													<Tooltip.Content>
														<p>Vote</p>
													</Tooltip.Content>
												</Tooltip.Root>
											</div>
										</Table.Cell>
									</Table.Row>
								{/each}
							</Table.Body>
						</Table.Root>
						<div class="flex justify-center w-full">
							<Button
								builders={[]}
								class="outline flex flex-row gap-1 p-4 mx-auto m-2"
							>
								<Icon
									class="color-gray-800 size-5"
									src={Plus}
									theme="solid"
								/>
								<div>Add an issue</div>
							</Button>
						</div>
					</div>
				</Tabs.Content>

				<Tabs.Content value="room-settings">
					<div class="grid gap-2 p-5 my-5 border rounded-lg">
						<div class="flex gap-4 flex-col">
							<div
								class="flex flex-row justify-between items-center"
							>
								<div>
									<p class="font-bold text-xl">
										Room Settings
									</p>
									<p class="text-sm">
										Make changes to your Room Settings.
									</p>
								</div>
							</div>
							<input
								bind:value={Game.user.username}
								type="text"
							/>
							<input bind:value={Game.room.id} type="text" />
						</div>
					</div>
				</Tabs.Content>
				<Tabs.Content value="profile"
					><div class="flex items-center justify-center">
						<button onclick={() => Game.restart()}>
							<Icon
								class="color-gray-800 size-8"
								src={ArrowPath}
								theme="solid"
							/>
						</button>
						<button onclick={() => Game.quitRoom()}>
							<Icon
								class="color-gray-800 size-8"
								src={ArrowRightEndOnRectangle}
								theme="solid"
							/>
						</button>
					</div></Tabs.Content
				>
			</Tabs.Root>
		</Sheet.Content>
	</Sheet.Root>
{/if}

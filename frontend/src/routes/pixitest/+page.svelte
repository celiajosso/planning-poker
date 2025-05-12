<script lang="ts">
	import { onMount } from 'svelte';
	import { Icon } from '@steeze-ui/svelte-icon';
	import { ArrowPath, ArrowRightEndOnRectangle } from '@steeze-ui/heroicons';
	import { game } from './socket.js';
	

	let canvas: HTMLCanvasElement;

	onMount(async () => {
		await game.init(canvas);
		game.join();
	});

	function exportVotes() {
		const votes = game.votes;

		if (!votes || votes.length === 0) {
			alert("Aucun vote à exporter.");
			return;
		}

		const csvHeader = "Summary,Custom field (Story point estimate)\n";
		const csvRows = votes.map(v => `${v.name},${v.card}`).join("\n");
		const csvContent = csvHeader + csvRows;

		const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
		const url = URL.createObjectURL(blob);

		const link = document.createElement("a");
		link.setAttribute("href", url);
		link.setAttribute("download", "votes.csv");
		document.body.appendChild(link);
		link.click();
		document.body.removeChild(link);

		// createJiraIssues(votes);

	}


	function importCSV(file: File) {
	if (!file) return;

	const reader = new FileReader();
	reader.onload = (event) => {
		const text = event.target?.result as string;
		parseAndAddStories(text);
		console.log("Import CSV 1");

	};
	reader.readAsText(file);
	console.log("Import du fichier :", file.name);

}

function parseAndAddStories(csv: string) {
    console.log("Import CSV 2");

    const lines = csv.trim().split("\n");

    const separator = lines[0].includes("\t") ? "\t" :
                     lines[0].includes(",") ? "," :
                     ";";

    const header = lines[0].split(separator);
    const summaryIndex = header.findIndex(h => h.toLowerCase().includes("summary"));
    const pointsIndex = header.findIndex(h => h.toLowerCase().includes("point"));
    console.log("Import CSV 3");

    if (summaryIndex === -1) {
        alert("Colonne 'Summary' manquante.");
        return;
    }

    for (let i = 1; i < lines.length; i++) {
        console.log("Import CSV 4");

        const cols = lines[i].split(separator);
        const summary = cols[summaryIndex]?.trim();
        const points = parseInt(cols[pointsIndex]?.trim()) || 0;

        if (summary) {
            console.log("Import CSV addstory", summary, points);

            game.addStory(summary, points);

            // game.sendStory('story', -1, { summary, points });
        }
    }
    alert("Stories importées !");
}










</script>

<canvas bind:this={canvas}></canvas>

<div class="absolute left-0 bottom-4 flex justify-center items-center w-full">
	<div class="bg-white rounded-xl flex px-4 py-2 gap-4 flex flex-col">
		<input bind:value={game.playerName} type="text" />
		<div class="flex items-center justify-center">
			<button onclick={() => game.restart()}>
				<Icon class="color-gray-900 size-8" src={ArrowPath} theme="solid" />
			</button>
			<button onclick={() => game.quit()}>
				<Icon class="color-gray-900 size-8" src={ArrowRightEndOnRectangle} theme="solid" />
			</button>
			<button onclick={exportVotes}>
				<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill: rgba(0, 0, 0, 1);transform: ;msFilter:;"><path d="M11 16h2V7h3l-4-5-4 5h3z"></path><path d="M5 22h14c1.103 0 2-.897 2-2v-9c0-1.103-.897-2-2-2h-4v2h4v9H5v-9h4V9H5c-1.103 0-2 .897-2 2v9c0 1.103.897 2 2 2z"></path></svg>
			</button>
			<input
				type="file"
				accept=".csv"
				onchange={(e) => {
					const input = e.target as HTMLInputElement | null;
					if (input?.files?.[0]) {
						importCSV(input.files[0]);
					}
				}}
			/>

			
			
			
		</div>
	</div>
</div>



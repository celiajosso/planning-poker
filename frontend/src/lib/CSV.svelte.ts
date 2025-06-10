import { Game } from "./Game.svelte";
import { toast } from "svelte-sonner";

export function importCSV(file: File) {
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

export function exportToCSV() {
	const csvRows: string[] = [];

	const headers = ["Summary", "Description", "Estimate"];
	csvRows.push(headers.join(","));

	for (const issue of Game.storage.room.stories) {
		const row = [issue.title, issue.description, `${issue.finalEstimate|0}`];
		console.log(row)
		csvRows.push(
			row.map((value) => `${value.replace(/"/g, '""')}`).join(","),
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

function parseAndAddStories(csv: string) {
	console.log("Import CSV 2");

	const lines = csv.trim().split("\n");

	const separator = lines[0].includes("\t") ? "\t" :
		lines[0].includes(",") ? "," :
			";";

	const header = lines[0].split(separator).map(h => h.trim().toLowerCase());

	const summaryIndex = header.findIndex(h => h === "summary");
	const descriptionIndex = header.findIndex(h => h === "description");

	if (summaryIndex === -1) {
		toast("CSV format is invalid")
		return;
	}
	else if (descriptionIndex === -1) {
		toast("CSV format is invalid")
		return;
	}

	for (let i = 1; i < lines.length; i++) {
		const cols = lines[i].split(separator);
		const summary = cols[summaryIndex]?.trim();
		const description = cols[descriptionIndex]?.trim()

		if (summary) {
			Game.createStory2(summary, description, 0);
		}
	}
	toast("User stories imported !")
}

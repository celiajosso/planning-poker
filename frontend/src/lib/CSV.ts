
function exportVotes() {
	const votes = Game.storage.room.stories.votes;

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

// /!\ A CHANGER SELON LE FORMAT CSV DE JIRA !!!
export function exportToCSV() {
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

function parseAndAddStories(csv: string) {
	console.log("Import CSV 2");

	const lines = csv.trim().split("\n");

	const separator = lines[0].includes("\t") ? "\t" :
		lines[0].includes(",") ? "," :
			";";

	const header = lines[0].split(separator).map(h => h.trim().toLowerCase());

	// Recherche exacte avec accents
	const summaryIndex = header.findIndex(h => h === "résumé");
	const pointsIndex = header.findIndex(h => h === "champs personnalisés (story point estimate)");

	if (summaryIndex === -1) {
		alert("Colonne 'Résumé' manquante.");
		return;
	}
	if (pointsIndex === -1) {
		alert("Colonne 'Champs personnalisés (Story point estimate)' manquante.");
		return;
	}

	for (let i = 1; i < lines.length; i++) {
		const cols = lines[i].split(separator);
		const summary = cols[summaryIndex]?.trim();
		const points = parseFloat(cols[pointsIndex]?.trim().replace(",", ".")) || 0;

		if (summary) {
			console.log("Import CSV addStory", summary, points);
			game.addStory(summary, points);
		}
	}
	alert("Stories importées !");
}




// function parseAndAddStories(csv: string) {
//     console.log("Import CSV 2");

//     const lines = csv.trim().split("\n");

//     const separator = lines[0].includes("\t") ? "\t" :
//                      lines[0].includes(",") ? "," :
//                      ";";

//     const header = lines[0].split(separator);
//     const summaryIndex = header.findIndex(h => h.toLowerCase().includes("summary"));
//     const pointsIndex = header.findIndex(h => h.toLowerCase().includes("point"));
//     console.log("Import CSV 3");

//     if (summaryIndex === -1) {
//         alert("Colonne 'Summary' manquante.");
//         return;
//     }

//     for (let i = 1; i < lines.length; i++) {
//         console.log("Import CSV 4");

//         const cols = lines[i].split(separator);
//         const summary = cols[summaryIndex]?.trim();
//         const points = parseInt(cols[pointsIndex]?.trim()) || 0;

//         if (summary) {
//             console.log("Import CSV addstory", summary, points);

//             game.addStory(summary, points);

//             // game.sendStory('story', -1, { summary, points });
//         }
//     }
//     alert("Stories importées !");
// }



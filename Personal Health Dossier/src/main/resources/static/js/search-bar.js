let patientsTable = document.getElementById("patientTable")
const searchBar = document.getElementById('searchPatient')

const allPatients = [];

fetch("http://localhost:8080/patients/api")
    .then(response => response.json())
    .then(data => {
        for (let patient of data) {
            allPatients.push(patient);
        }
    })


searchBar.addEventListener('keyup', (e) => {
    const searchingCharacters = searchBar.value;

    $("#patientTable > tr").remove();
    console.log(patientsTable)

    if (searchingCharacters.trim() === "") {
        patientsTable.style.display = "none";

    }
    let filteredPatients = [];
    if (searchingCharacters.trim() !== "") {
        filteredPatients = allPatients.filter(patient => {
            return patient.personalIdentificationNumber.startsWith(searchingCharacters)
                || patient.firstName.includes(searchingCharacters);
        });
    }
    console.log(filteredPatients)
    displayPatients(filteredPatients);

})


function displayPatients(patients) {
    if (patients.length > 0) {
        patientsTable.style.display = "block"
        for (let p of patients) {
            let trElement = document.createElement("tr");
            let tdFirstName = document.createElement("td");
            tdFirstName.innerText = `${p.firstName}`
            let tdMiddleName = document.createElement("td");

            if (p.middleName === null) {
                tdMiddleName.value = "";
            } else {
                tdMiddleName.innerText = `${p.middleName}`;
            }
            let tdLastName = document.createElement("td");
            tdLastName.innerText = `${p.lastName}`
            let tdPIN = document.createElement("td");
            tdPIN.innerText = `${p.personalIdentificationNumber}`
            let tdButton = document.createElement("td");
            tdButton.innerHTML = `<div class="btn-group">                                      
                                         <a href="/patients/view/${p.id}" type="button" class="btn  btn-sm btn-info" style="border-radius: 5px">View</a>                                                                          
                               </div>`;
            trElement.appendChild(tdFirstName);
            trElement.appendChild(tdMiddleName);
            trElement.appendChild(tdLastName);
            trElement.appendChild(tdPIN);
            trElement.appendChild(tdButton);
            patientsTable.appendChild(trElement);
        }
    }


}


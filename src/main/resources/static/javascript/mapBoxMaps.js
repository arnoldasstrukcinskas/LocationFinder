mapboxgl.accessToken = apiKey;

let startCoordinates;
let endCoordinates;

const startPointMap = new mapboxgl.Map({
    container: 'startPointMap',
    style: 'mapbox://styles/mapbox/streets-v12',
    center: [-87.661557, 41.893748],
    zoom: 11
});

const endPointMap = new mapboxgl.Map({
    container: 'endPointMap',
    style: 'mapbox://styles/mapbox/streets-v12',
    center: [-87.661557, 41.893748],
    zoom: 11
});

startPointMap.on('style.load', function () {

    const searchStartPoint = new MapboxSearchBox();
    getStartMapCurrentPosition();
    addStartSearchBox(searchStartPoint);


    ///pridedu start point event listeneri, kad kai ieskau, gauna searcho info
    searchStartPoint.addEventListener('retrieve', (event) => {
        const startFeatureCollection = event.detail;
        console.log(startFeatureCollection);

        $.ajax({                                      // Siunciu objekto Json formata
            url: "/routes/handleStartPointResponse",  // controllerio methodo URL
            type: "POST",             // Post nes siunciu info i controlleri
            contentType: "application/json",
            data: JSON.stringify(startFeatureCollection),
            dataType: "json"
        });
    })
});

endPointMap.on('style.load', function () {

const searchEndPoint = new MapboxSearchBox();
getEndMapCurrentPosition();
addEndSearchBox(searchEndPoint);

///pridedu End point event listeneri, kad kai ieskau, gauna searcho info
    searchEndPoint.addEventListener('retrieve', (event) => {
        const endFeatureCollection = event.detail;
        console.log(endFeatureCollection);

        $.ajax({                                    //siunciu objekto Json formata
            url: "/routes/handleEndPointResponse",  // controllerio methodo URL
            type: "POST",             // Post nes siunciu info i controlleri
            contentType: "application/json",
            data: JSON.stringify(endFeatureCollection),
            dataType: "json"
        });
        getRoute();
    })

});

//Creating directions description functions and painting route.

function getRoute() {

    fetch(
        `https://api.mapbox.com/directions/v5/mapbox/driving-traffic/${startCoordinates};${endCoordinates}?steps=true&geometries=geojson&access_token=${mapboxgl.accessToken}`,
        { method: 'GET' }
    )
        .then(response => response.json())
        .then(json => {
            const data = json.routes[0];
            const route = data.geometry.coordinates;
//            const geojson = {
//                type: 'Feature',
//                properties: {},
//                geometry: {
//                    type: 'LineString',
//                    coordinates: route
//                }
//            };
//
//            console.log('GeoJSON object:', geojson);
//
//            if (startPointMap.getSource('route')) {
//                startPointMap.getSource('route').setData(geojson);
//            }
//            else {
//                startPointMap.addLayer({
//                    'id': 'route',
//                    'type': 'line',
//                    'source': {
//                        'type': 'geojson',
//                        'data': geojson
//                    },
//                    'layout': {
//                        'line-join': 'round',
//                        'line-cap': 'round'
//                    },
//                    'paint': {
//                        'line-color': '#3887be',
//                        'line-width': 5,
//                        'line-opacity': 0.75
//                    }
//                });
//            }
//            startPointMap.jumpTo({
//                center: [23.9044817, 54.8982139],
//                zoom: 11
//            });
            // get the sidebar and add the instructions
            const instructions = document.getElementById('instructions');
            const steps = data.legs[0].steps;

            let tripInstructions = '';
            for (const step of steps) {
                tripInstructions += `<li>${step.maneuver.instruction}</li>`;
            }
            instructions.innerHTML = `<p><strong>Trip duration: ${Math.floor(
                data.duration / 60
            )} min 🚴 </strong></p><ol>${tripInstructions}</ol>`;
        })
        .catch(error => {
            console.error('Error fetching route:', error);
        });
}

// Code from the next step will go here.


setInterval(updateStartLocation, 5000);
setInterval(updateEndLocation, 5000);
setInterval(updateStartCoordinates, 1000);
setInterval(updateEndCoordinates, 1000);

function updateStartLocation() {
    var startLocationName = document.getElementById("startingLocation");

    // Use AJAX to fetch the locationName from the server
    $.ajax({
        url: "/routes/startingLocationName",
        type: "GET",
        success: function (response) {
            startLocationName.innerHTML = "Pradinis keliones taskas: " + response;
            console.log("Test: " + response);
        },
        error: function (error) {
            console.error("Error: No start location name", error);
        }
    });
}

function updateEndLocation() {
    var endLocationName = document.getElementById("endingLocation");
    $.ajax({
        url: "/routes/endLocationName",
        type: "GET",
        success: function (response) {
            endLocationName.innerHTML = "Galinis keliones taskas: " + response;
            console.log("Test: " + response);
        },
        error: function (error) {
            console.error("Error: No end location name", error);
        }
    });
}

function updateStartCoordinates() {

    $.ajax({            //Pasiemu start cordinates is controllerio
        url: "/routes/startingLocationCoordinates",
        type: "GET",
        success: function (response) {
            startCoordinates = response.toString();
            document.getElementById("startingCoordinates").value = startCoordinates;
            console.log("startCoordinates logging " + startCoordinates);
        },
        error: function (error) {
            console.error("Error: No start coordinates", error);
        }
    });
}
function updateEndCoordinates() {

    $.ajax({                //Pasiemu endpoint coordinates is controlerio
        url: "/routes/endLocationCoordinates",
        type: "GET",
        success: function (response) {
            endCoordinates = response.toString();
            document.getElementById("endingCoordinates").value = endCoordinates;
        },
        error: function (error) {
            console.error("Error: No ending coordinates ", error)
        }
    });
}

function getStartMapCurrentPosition() {
    //////////////////// Suranda lokacija ir padeda ten taska
    navigator.geolocation.getCurrentPosition(function (position) {
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;

        startPointMap.addLayer({
            id: 'start',
            type: 'circle',
            source: {
                type: 'geojson',
                data: {
                    type: 'FeatureCollection',
                    features: [
                        {
                            type: 'Feature',
                            properties: {},
                            geometry: {
                                type: 'Point',
                                coordinates: [longitude, latitude]
                            }
                        }
                    ]
                }
            },
            paint: {
                'circle-radius': 10,
                'circle-color': '#f30'
            }
        });
        startPointMap.jumpTo({
            center: [longitude, latitude],
            zoom: 15
        });
    });
}

function getEndMapCurrentPosition() {
    //////////////////// Suranda lokacija ir padeda ten taska
    navigator.geolocation.getCurrentPosition(function (position) {
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;

        endPointMap.addLayer({
            id: 'end',
            type: 'circle',
            source: {
                type: 'geojson',
                data: {
                    type: 'FeatureCollection',
                    features: [
                        {
                            type: 'Feature',
                            properties: {},
                            geometry: {
                                type: 'Point',
                                coordinates: [longitude, latitude]
                            }
                        }
                    ]
                }
            },
            paint: {
                'circle-radius': 10,
                'circle-color': '#f30'
            }
        });
        endPointMap.jumpTo({
            center: [longitude, latitude],
            zoom: 15
        });
    })
}

function addStartSearchBox(startSearchBox){
//////////pridedu Start point search boxa//////////

    startSearchBox.accessToken = apiKey;
    startSearchBox.marker = true;
    startSearchBox.mapboxgl = mapboxgl;
    startPointMap.addControl(startSearchBox, 'top-left');
}

function addEndSearchBox(endSearchBox) {

    /////////////// pridedu End point search boxa

    endSearchBox.accessToken = apiKey;
    endSearchBox.marker = true;
    endSearchBox.mapboxgl = mapboxgl;
    endPointMap.addControl(endSearchBox, 'top-right');
}

function redirectOnSubmit(){
    window.open('http://localhost:8080/routes/create');
}
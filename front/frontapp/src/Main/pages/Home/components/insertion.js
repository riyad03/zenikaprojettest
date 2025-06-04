import React, { useEffect, useState } from "react";
import { notify } from "../../../../App";


function Insertion({updatestate,Total}){
    const [piece,SetPiece]=useState(0.5);
    const [solde,SetSolde]=useState(0);

    
    function handleInsertion (){
        
        fetch(`http://localhost:8080/ajoutpiece/${piece}`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ value: piece }),
        })
        .then(
            (res)=>{
                if (!res.ok) {
                    throw new Error("Erreur lors de l'ajout de pièces");
                }
                notify.success(`${piece} pièces ajoutées avec succès !`);
                return res.json();
            }
        )
        .then(() => {
            updatestate();
            return fetch("http://localhost:8080/recupiece");
        })
        .then((res) => res.json())
        .then((res)=>{
            SetSolde(res);
        })
        .catch(error=>{
            console.log(error);
            notify.error("Erreur lors de l'ajout de pièces");
        });
    };
    useEffect(()=>{
        fetch("http://localhost:8080/recupiece")
        .then(
            (res)=>{return res.json();}
        ).then(
            res=>{
                console.log("solde: "+res);
                SetSolde(res);
            }
        ).catch(error=>{
            console.log(error);
        })
    },[]);
    return(
        <div className="bg-white rounded shadow-sm w-[170px] p-2 border border-gray-200 mb-2 ml-auto">
             
            <div className="choixPiece flex items-center justify-between mb-1 bg-gray-50 p-1 rounded text-xs">
                <span className="font-semibold text-green-600">Solde:  {(solde - Total) > 0 ? (solde - Total) : 0} MAD</span>
                
               
                <select 
                    name="pieces" 
                    id="pieces" 
                    value={piece} 
                    onChange={(e) => SetPiece(Number(e.target.value))}
                    className="border border-gray-300 rounded px-1 py-0.5 bg-white focus:outline-none focus:ring-1 focus:ring-blue-500 text-xs w-12"
                >
                    <option value="0.5">0.5</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="5">5</option>
                    <option value="10">10</option>
                </select>
                
            </div>
            <button 
                onClick={handleInsertion}
                className="w-full bg-green-600 hover:bg-green-700 text-white font-medium py-0.5 px-1 rounded text-xs transition-colors duration-200 focus:outline-none focus:ring-1 focus:ring-green-500"
            >
                Insert
            </button>
            
        </div>
    );
}
export default Insertion;
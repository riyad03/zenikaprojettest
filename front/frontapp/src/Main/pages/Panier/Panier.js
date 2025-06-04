import React, { useEffect, useState } from "react";
import { notify } from "../../../App";

function Panier() {
    const [produits, setProduits] = useState([]);
    const [solde,SetSolde]=useState(0);
    const [total,SetTotal]=useState(0);
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

    useEffect(() => {
        fetch("http://localhost:8080/panier")
            .then((rep) => rep.json())
            .then((rep) => {
                console.log(rep);
                setProduits(rep);
            })
            .catch((err) => {
                console.log("Erreur lors de la récupération du panier:", err);
            });
    }, []);

    useEffect(()=>{
        fetch("http://localhost:8080/prixtotale")
        .then(
            res=>{
                if(!res.ok){
                    return console("Problem de recuperation");
                }
                return res.json();
            }
        ).then(
            res=>SetTotal(res)
        ).catch(
            err=>console.log(err)
        )
    })

    

    function supprimerProduit(id){
        fetch(`http://localhost:8080/retirer/${id}`,
            {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                },
               
            }
        ).then(
            
            res=>{
                if (!res.ok) {
                    throw new Error("Failed to delete product");
                }
                notify.success("Produit retiré du panier");
                return res.json()}
        ).then(
            ()=>fetch("http://localhost:8080/panier")
        )
        .then(
            res=>{
                if (!res.ok) {
                    throw new Error("Failed to fetch panier");
                }
                return res.json();
            }
        )
        .then(
            res=>setProduits(res)
        ).then(
            ()=>fetch("http://localhost:8080/prixtotale")
        ).then(
            res=>{
                if(!res.ok){
                    return console("Problem de recuperation");
                }
                return res.json();
            }
        ).then(
            res=>SetTotal(res)
        ).catch(
            err=>{
                console.log(err);
                notify.error("Erreur lors de la suppression du produit");
            }
        )
        
        .catch(err=>
            console.log(err)
        )
    }

    function Acheter(){
        if(produits.length<=0){
            notify.error("Rien à acheter!");
            return;
        }
        fetch("http://localhost:8080/acheter",
            {
                method:"POST",
                headers: {
                    "Content-Type": "application/json",
                }
                
            }
        )
        .then(
            res=>{
                if(!res.ok){
                    throw new Error("error d'achat");
                }
                notify.success("Achat effectué avec succès !");
                return res.json();
            }
        ).then(
            ()=>fetch("http://localhost:8080/panier")
        ).then(
            res=>{
                if(!res.ok){
                    console.log("erreur de mise a jour de panier d\'achat");
                }
                return res.json();
            }
        ).then(
            res=>(setProduits(res))
        )
        .then(
            ()=>fetch("http://localhost:8080/recupiece")
        ).then(
            res=>{
                if(!res.ok){
                    console.log("erreur de mise a jour de piece lors d\'achat");
                }
                return res.json();
            }
        )
        .then(
            res=>{SetSolde(res)}
        ).then(
            ()=>fetch("http://localhost:8080/prixtotale")
        ).then(
            res=>{
                if(!res.ok){
                    return console("Problem de recuperation");
                }
                return res.json();
            }
        ).then(
            res=>SetTotal(res)
        )
        .catch(
            err=>{
                console.log(err);
                notify.error("Erreur lors de l'achat");
            }
        )
    }
    function Annuler(){
        if(produits.length<=0){
            notify.error("Rien dans le panier!");
            return;
        }
        fetch("http://localhost:8080/annuler",
            {
                method:"DELETE",
                headers:{
                    "Content-Type":"application/json",
                }
            }
        )
        .then(
            res=>{
                if(!res.ok){
                    throw new Error("erreur d'annulation");
                }
                notify.info("Panier annulé");
                return res.json();
            }
        ).then(
            ()=>fetch("http://localhost:8080/panier")
        ).then(
            res=>{
                if(!res.ok){
                    console.log("error de mise a jour de panier lors d\'anulation");
                }
                return res.json();
            }
        )
        .then(
            res=>{setProduits(res)}
        ).catch(
            err=>{
                console.log(err);
                notify.error("Erreur lors de l'annulation du panier");
            }
        )
    }

    return (
        <div className="min-h-screen bg-gray-50 p-6">
            <div className="max-w-2xl mx-auto">
                <div className="bg-white rounded-lg shadow-md p-6 mb-6 border border-gray-200">
                    <h2 className="text-2xl font-bold text-gray-800 mb-4">Panier</h2>
                    <div className="flex justify-between items-center bg-gray-50 p-4 rounded-md">
                        <h2 className="text-lg font-semibold text-green-600">Solde: {solde} MAD</h2>
                        <h2 className="text-lg font-semibold text-blue-600">Totale: {total} MAD</h2>
                    </div>
                </div>
                
                {produits.length === 0 ? (
                    <div className="bg-white rounded-lg shadow-md p-8 text-center border border-gray-200">
                        <p className="text-gray-500 text-lg">Aucun produit dans le panier.</p>
                    </div>
                ) : (
                    <div className="bg-white rounded-lg shadow-md border border-gray-200 mb-6">
                        <ul className="divide-y divide-gray-200">
                            {produits.map((produit, index) => (
                                <li key={index} className="p-4 hover:bg-gray-50 transition-colors duration-200">
                                    <div className="flex justify-between items-center">
                                        <div className="flex-1">
                                            <div className="text-lg font-semibold text-gray-800 mb-1">
                                                <strong>Nom:</strong> {produit.name}
                                            </div>
                                            <div className="text-md text-green-600 font-medium">
                                                <strong>Prix:</strong> {produit.price} MAD
                                            </div>
                                        </div>
                                        <a 
                                            onClick={()=>supprimerProduit(produit.id)}
                                            className="bg-red-600 hover:bg-red-700 text-white font-medium py-2 px-4 rounded-md transition-colors duration-200 cursor-pointer ml-4"
                                        >
                                            Supprimer
                                        </a>
                                    </div>
                                </li>
                            ))}
                        </ul>
                    </div>
                )}
                
                <div className="flex gap-4 justify-center">
                    <a 
                        onClick={()=>Acheter()}
                        className="bg-green-600 hover:bg-green-700 text-white font-medium py-3 px-6 rounded-md transition-colors duration-200 cursor-pointer shadow-md hover:shadow-lg"
                    >
                        Acheter
                    </a>
                    <a 
                        onClick={()=>Annuler()}
                        className="bg-gray-600 hover:bg-gray-700 text-white font-medium py-3 px-6 rounded-md transition-colors duration-200 cursor-pointer shadow-md hover:shadow-lg"
                    >
                        Anuler
                    </a>
                </div>
            </div>
        </div>
    );
}

export default Panier;
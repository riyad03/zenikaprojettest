import React, { useEffect, useState ,useRef} from "react";
import { notify } from "../../../App";
import Insertion from "./components/insertion.js";


    function Home(){
        const [produits,setProduits]=useState([]);
        const [total,SetTotal]=useState(0);

        const fetchProduits = () => {
        fetch("http://localhost:8080/produits")
        .then((res) => res.json())
        .then((data) => {
            console.log(data);
            setProduits(data);
        })
        .catch((err) => console.error(err));
    };

    useEffect(() => {
        fetchProduits();
    }, []);

    useEffect(()=>{
        fetch("http://localhost:8080/prixtotale")
        .then((res) => res.json())
        .then((res)=>SetTotal(res))
        .catch(error=>{
            console.log(error);
            notify.error("Erreur lors de chargement du total");
        });
    },[]);
   
    
    function AjouterPanier(id){
        fetch(`http://localhost:8080/ajoutpanier/${id}`,
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                }
            }
        ).then(
            (res)=>{return res.json();}
        ).then((res) => {
            console.log(res);
            if(res===true)
                notify.success("Produit ajouté au panier avec succès !");
            else
                notify.error("Pas assez de solde pour ajouter ce produit!")
        }).then(
            ()=>fetch('http://localhost:8080/prixtotale')
        ).then((res)=>{
            if(!res.ok){
                console.log("Erreur dans l\'acces au prix");
            }
            return res.json();
        }).then(
            (res)=>SetTotal(res)
        )
        .then(  
            () => fetch("http://localhost:8080/produits")
        ).then((res) =>
        {
            if(!res.ok){
                console.log(res);
            }  
            return res.json();

        }).then((data) => {
            console.log(data);
            setProduits(data);
        })
        .catch(error=>{
            console.log(error);
            notify.error("Erreur lors de l'ajout au panier");
        });
    }



    return(
        <div className="min-h-screen bg-gray-50 py-8">
            <div className="max-w-4xl mx-auto px-4">
                <div className="mb-8">
                    <p>Total: {total} MAD</p>
                    
                    <Insertion Total={total} updatestate={fetchProduits}/>
                </div>
                
                {produits.length > 0 ? (
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                        {produits.map((item, index) => (
                            <div key={index} className="bg-white rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300 p-6 border border-gray-200"> 
                                <div className="mb-2">
                                    {item.possibleAchat ?(<p className="text-sm text-green-500 font-medium">Disponible</p>) : <p className="text-sm text-red-500 font-medium">Non Disponible</p>}   
                                </div>
                                <h3 className="text-xl font-semibold text-gray-800 mb-3">{item.name}</h3>
                                <div className="text-2xl font-bold text-green-600 mb-4">{item.price} MAD</div>
                                <a onClick={()=>AjouterPanier(item.id)} className="inline-block bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-4 rounded-md transition-colors duration-200 cursor-pointer">
                                    Ajouter
                                </a>
                            </div>
                        ))}
                    </div>
                ) : (
                    <div className="flex justify-center items-center py-20">
                        <p className="text-lg text-gray-600">Loading...</p>
                    </div>
                )}
            </div>
        </div>
    )
}

export default Home;
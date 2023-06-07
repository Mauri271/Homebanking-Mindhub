  const { createApp } = Vue

  createApp({
    data() {
    return {
          clients: [],
          restResponse:``,
          clientData:{
          firstName:"",
          lastName:"",
          email:""}
        };
    },

    created(){
        this.loadData();
    },

    methods:{

    loadData(){
            axios.get("http://localhost:8080/rest/clients/")
            .then(data => {
              
              
            this.clients= data
            this.clients = this.clients.data._embedded.clients
            this.restResponse = this.clients
            console.log(this.clients)

            })
            .catch(err=> console.log(err))
    },


  addClient(){

      if(this.clientData.firstName != `` && this.clientData.lastName != `` && this.clientData.email != ``){
        this.postClient()
      } else{
        alert(`please complete all the fields`)
      }
    
    
  
    },
    
    postClient(){
    axios.post("http://localhost:8080/rest/clients/", this.clientData)
    .then(res =>{

        this.loadData()
        this.deleteInputs()      


    }).catch(err=> console.log(err))
    },

    deleteInputs(){

      this.clientData.firstName = ""
      this.clientData.lastName = ""
      this.clientData.email = ""
    },


      deleteClient(id){

        axios.delete(id)
        .then(res=>{
          console.log(res)
          this.loadData();

        })
        .catch(err=> console.log(err))
      },

      editClient(client){

        axios.patch(client, this.clientData)
        .then(res =>{

          console.log(res)

          this.clientData.firstName = "";
          this.clientData.lastName = "";
          this.clientData.email = "";
          this.loadData();

        })

        .catch(err=>console.log(err))

      }


    }



  }).mount('#app')
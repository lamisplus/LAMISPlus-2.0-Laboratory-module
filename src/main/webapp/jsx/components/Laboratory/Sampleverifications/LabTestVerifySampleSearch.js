
import React, {useEffect, useCallback, useState} from 'react';
import MaterialTable from 'material-table';
import { Link } from 'react-router-dom'
import { connect } from "react-redux";
// import { fetchAllLabTestOrder } from "./../../../actions/laboratory";
import "./../laboratory.css";
import VisibilityIcon from '@material-ui/icons/Visibility';
import Tooltip from '@material-ui/core/Tooltip';
import IconButton from '@material-ui/core/IconButton';
import MatButton from '@material-ui/core/Button'

import { forwardRef } from 'react';
import axios from "axios";
import {token, url } from "../../../../api";
import { toast } from 'react-toastify';
import AddBox from '@material-ui/icons/AddBox';
import ArrowUpward from '@material-ui/icons/ArrowUpward';
import Check from '@material-ui/icons/Check';
import ChevronLeft from '@material-ui/icons/ChevronLeft';
import ChevronRight from '@material-ui/icons/ChevronRight';
import Clear from '@material-ui/icons/Clear';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import Edit from '@material-ui/icons/Edit';
import FilterList from '@material-ui/icons/FilterList';
import FirstPage from '@material-ui/icons/FirstPage';
import LastPage from '@material-ui/icons/LastPage';
import Remove from '@material-ui/icons/Remove';
import SaveAlt from '@material-ui/icons/SaveAlt';
import Search from '@material-ui/icons/Search';
import ViewColumn from '@material-ui/icons/ViewColumn';

const tableIcons = {
Add: forwardRef((props, ref) => <AddBox {...props} ref={ref} />),
Check: forwardRef((props, ref) => <Check {...props} ref={ref} />),
Clear: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref} />),
DetailPanel: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
Edit: forwardRef((props, ref) => <Edit {...props} ref={ref} />),
Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref} />),
Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref} />),
FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref} />),
LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref} />),
NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
PreviousPage: forwardRef((props, ref) => <ChevronLeft {...props} ref={ref} />),
ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
Search: forwardRef((props, ref) => <Search {...props} ref={ref} />),
SortArrow: forwardRef((props, ref) => <ArrowUpward {...props} ref={ref} />),
ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref} />),
ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref} />)
};

const PatientSearch = (props) => {
    const [loading, setLoading] = useState(true)
    const [collectedSamples, setCollectedSamples] = useState([])
    
    const loadLabTestData = useCallback(async () => {
            try {
                const response = await axios.get(`${url}laboratory/orders/pending-sample-verification`, { headers: {"Authorization" : `Bearer ${token}`} });
                //console.log("verify test", response);
                setCollectedSamples(response.data);
                setLoading(false)
            } catch (e) {
                toast.error("An error occurred while fetching lab", {
                    position: toast.POSITION.TOP_RIGHT
                });
                setLoading(false)
            }
        }, []);

        useEffect(() => {
             setLoading('true');
             const onSuccess = () => {
                 setLoading(false)
             }
             const onError = () => {
                 setLoading(false)
             }
             loadLabTestData();
        }, [loadLabTestData]);

    const totalSamples = (test) => {
            let  maxVal = 0;
            for(var i=0; i<test.length; i++){
               maxVal = test[i].samples.length
            }
        return maxVal;
    }

    function totalSampleVerified (test){
         let  maxVal = 0;
            for(var i=0; i<test.length; i++){
            console.log("data", test[i].samples)
                for (var data in test[i].samples) {
                    console.log("data", data)
                }

            }
         return maxVal;
    }
    
  return (
      <div>
          
          <MaterialTable
           icons={tableIcons}
              title="Laboratory Test Sample Verification"
              columns={[
                  { title: "Hospital ID", field: "Id" },
                  {
                    title: "Patient Name",
                    field: "name",
                  },
                  { title: "Date Order", field: "date", type: "dateTime" , filtering: false},

                  {
                    title: "Sample Collected",
                    field: "samples",
                    filtering: false
                  },
                  {
                    title: "Sample Verified",
                    field: "samplecount",
                    filtering: false
                  },
                   {
                   title: "Sample Results",
                   field: "sampleresults",
                   filtering: false
                 },
                  {
                    title: "Action",
                    field: "actions",
                    filtering: false,
                  },
              ]}
                isLoading={loading}
                data={collectedSamples.map((row) => ({
                Id: row.patientHospitalNumber,
                name: row.patientFirstName +  ' ' + row.patientLastName,
                date: row.orderDate,
                samples: row.collectedSamples,
                samplecount: row.verifiedSamples,
                sampleresults: row.reportedResults,
                actions: row.collectedSamples > 0 ? <Link to ={{
                                        pathname: "/samples-verification",  
                                        state: row
                                    }}  
                                        style={{ cursor: "pointer", color: "blue", fontStyle: "bold" 
                                    }}
                        >
                            <MatButton variant="outlined" color="primary">
                               <VisibilityIcon color="primary"/>
                               View
                            </MatButton>
                        </Link>
                        : " "
                })
            )}


                  options={{
                    headerStyle: {
                         backgroundColor: "#014d88",
                         color: "#fff"
                    },
                    searchFieldStyle: {
                        width : '300%',
                        margingLeft: '250px',
                    },
                    filtering: false,
                    exportButton: false,
                    searchFieldAlignment: 'left',
                    pageSizeOptions:[10,20,100],
                    pageSize:10,
                    debounceInterval: 400
                }}

          />
        
    </div>
  );
}

// const mapStateToProps = state => {
//     return {
//         patientsTestOrderList: state.laboratory.list
//     };
// };
// const mapActionToProps = {
//     fetchAllLabTestOrderToday: fetchAllLabTestOrder
// };
  
export default PatientSearch;
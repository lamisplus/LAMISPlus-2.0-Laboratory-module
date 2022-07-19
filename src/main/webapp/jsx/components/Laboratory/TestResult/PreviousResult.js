
import React, {useEffect, useCallback, useState} from 'react';
import MaterialTable from 'material-table';
import { Link } from 'react-router-dom'
import NoteAddIcon from '@material-ui/icons/NoteAdd';
import "./../laboratory.css";
import Tooltip from '@material-ui/core/Tooltip';
import IconButton from '@material-ui/core/IconButton';
import PatientCardDetail from './../../Patient/PatientCard';

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

const PreviousResult = (props) => {
    const patientObj = {}
    const [loading, setLoading] = useState('')
    const [collectedSamples, setCollectedSamples] = useState([])

    const loadLabTestData = useCallback(async () => {
        try {
            const response = await axios.get(`${url}laboratory/orders/pending-results`, { headers: {"Authorization" : `Bearer ${token}`} });
            //console.log("sample results", response);
            setCollectedSamples(response.data);
        } catch (e) {
            toast.error("An error occurred while fetching lab", {
                position: toast.POSITION.TOP_RIGHT
            });
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

  return (
      <div>


    </div>
  );
}

export default PreviousResult;
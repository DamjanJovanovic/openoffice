/**************************************************************
 * 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * 
 *************************************************************/



// MARKER(update_precomp.py): autogen include statement, do not remove
#include "precompiled_sdext.hxx"

#include "pdfihelper.hxx"

#include <rtl/ustrbuf.hxx>
#include <basegfx/numeric/ftools.hxx>

using namespace pdfi;
using namespace com::sun::star;

rtl::OUString pdfi::getColorString( const rendering::ARGBColor& rCol )
{
    rtl::OUStringBuffer aBuf( 7 );
    const sal_uInt8 nRed  ( sal::static_int_cast<sal_Int8>( basegfx::fround( rCol.Red * 255.0 ) ) );
    const sal_uInt8 nGreen( sal::static_int_cast<sal_Int8>( basegfx::fround( rCol.Green * 255.0 ) ) );
    const sal_uInt8 nBlue ( sal::static_int_cast<sal_Int8>( basegfx::fround( rCol.Blue * 255.0 ) ) );
    aBuf.append( sal_Unicode('#') );
    if( nRed < 10 )
        aBuf.append( sal_Unicode('0') );
    aBuf.append( sal_Int32(nRed), 16 );
    if( nGreen < 10 )
        aBuf.append( sal_Unicode('0') );
    aBuf.append( sal_Int32(nGreen), 16 );
    if( nBlue < 10 )
        aBuf.append( sal_Unicode('0') );
    aBuf.append( sal_Int32(nBlue), 16 );

	// TODO(F3): respect alpha transparency (polygons etc.)
    OSL_ASSERT(rCol.Alpha == 1.0);

    return aBuf.makeStringAndClear();
}

rtl::OUString pdfi::unitMMString( double fMM )
{
    rtl::OUStringBuffer aBuf( 32 );
    aBuf.append( rtl_math_round( fMM, 2, rtl_math_RoundingMode_Floor ) );
    aBuf.appendAscii( "mm" );
    
    return aBuf.makeStringAndClear();
}

rtl::OUString pdfi::convertPixelToUnitString( double fPix )
{
    rtl::OUStringBuffer aBuf( 32 );
    aBuf.append( rtl_math_round( convPx2mm( fPix ), 2, rtl_math_RoundingMode_Floor ) );
    aBuf.appendAscii( "mm" );
    
    return aBuf.makeStringAndClear();
}



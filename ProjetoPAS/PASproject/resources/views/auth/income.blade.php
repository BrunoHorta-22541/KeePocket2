@extends('layouts.auth');

@section('content')
<div class="main-panel">
    <div class="content-wrapper">
        <!--Conteudo Aqui -->
        <div class="row">
            <div class="col-lg-12 grid-margin stretch-card">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Income</h4>
                        <div class="table-responsive">
                        <table class="table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Category</th>
                                        <th>Value</th>
                                        <th>Description</th>
                                        <th>Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                @foreach ($income as $incomes)
                                    <tr>
                                        <td>{{ $incomes->id }}</td>
                                        <td>
                                            {{ App\Models\Category::find($incomes->idCategory)->categoryName }}
                                        </td>
                                        <td>{{ $incomes->value }}</td>
                                        <td>{{ $incomes->description }}</td>
                                        <td>{{ $incomes->movementsdate }}</td>
                                    </tr>
                                @endforeach
                                </tbody>
                            </table>
                        </div>
                    </div>

                </div>

            </div>

        </div>

        <!--Conteudo Aqui -->
    </div>
    <!-- content-wrapper ends -->
    <!-- partial:partials/_footer.html -->

    <!-- partial -->
</div>
@endsection
import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISistema } from '../sistema.model';
import { SistemaService } from '../service/sistema.service';
import { SistemaFormService, SistemaFormGroup } from './sistema-form.service';

@Component({
  standalone: true,
  selector: 'jhi-sistema-update',
  templateUrl: './sistema-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SistemaUpdateComponent implements OnInit {
  isSaving = false;
  sistema: ISistema | null = null;

  protected sistemaService = inject(SistemaService);
  protected sistemaFormService = inject(SistemaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SistemaFormGroup = this.sistemaFormService.createSistemaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sistema }) => {
      this.sistema = sistema;
      if (sistema) {
        this.updateForm(sistema);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sistema = this.sistemaFormService.getSistema(this.editForm);
    if (sistema.id !== null) {
      this.subscribeToSaveResponse(this.sistemaService.update(sistema));
    } else {
      this.subscribeToSaveResponse(this.sistemaService.create(sistema));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISistema>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(sistema: ISistema): void {
    this.sistema = sistema;
    this.sistemaFormService.resetForm(this.editForm, sistema);
  }
}

jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TarefaEmpresaModeloService } from '../service/tarefa-empresa-modelo.service';

import { TarefaEmpresaModeloDeleteDialogComponent } from './tarefa-empresa-modelo-delete-dialog.component';

describe('TarefaEmpresaModelo Management Delete Component', () => {
  let comp: TarefaEmpresaModeloDeleteDialogComponent;
  let fixture: ComponentFixture<TarefaEmpresaModeloDeleteDialogComponent>;
  let service: TarefaEmpresaModeloService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TarefaEmpresaModeloDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(TarefaEmpresaModeloDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TarefaEmpresaModeloDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TarefaEmpresaModeloService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});

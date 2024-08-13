jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TarefaRecorrenteEmpresaModeloService } from '../service/tarefa-recorrente-empresa-modelo.service';

import { TarefaRecorrenteEmpresaModeloDeleteDialogComponent } from './tarefa-recorrente-empresa-modelo-delete-dialog.component';

describe('TarefaRecorrenteEmpresaModelo Management Delete Component', () => {
  let comp: TarefaRecorrenteEmpresaModeloDeleteDialogComponent;
  let fixture: ComponentFixture<TarefaRecorrenteEmpresaModeloDeleteDialogComponent>;
  let service: TarefaRecorrenteEmpresaModeloService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TarefaRecorrenteEmpresaModeloDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(TarefaRecorrenteEmpresaModeloDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TarefaRecorrenteEmpresaModeloDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TarefaRecorrenteEmpresaModeloService);
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

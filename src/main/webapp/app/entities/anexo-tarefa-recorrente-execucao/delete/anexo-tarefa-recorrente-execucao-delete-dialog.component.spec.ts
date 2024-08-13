jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AnexoTarefaRecorrenteExecucaoService } from '../service/anexo-tarefa-recorrente-execucao.service';

import { AnexoTarefaRecorrenteExecucaoDeleteDialogComponent } from './anexo-tarefa-recorrente-execucao-delete-dialog.component';

describe('AnexoTarefaRecorrenteExecucao Management Delete Component', () => {
  let comp: AnexoTarefaRecorrenteExecucaoDeleteDialogComponent;
  let fixture: ComponentFixture<AnexoTarefaRecorrenteExecucaoDeleteDialogComponent>;
  let service: AnexoTarefaRecorrenteExecucaoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoTarefaRecorrenteExecucaoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AnexoTarefaRecorrenteExecucaoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AnexoTarefaRecorrenteExecucaoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AnexoTarefaRecorrenteExecucaoService);
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

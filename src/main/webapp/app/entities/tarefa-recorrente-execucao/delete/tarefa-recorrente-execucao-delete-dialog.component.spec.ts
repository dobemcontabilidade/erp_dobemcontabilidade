jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TarefaRecorrenteExecucaoService } from '../service/tarefa-recorrente-execucao.service';

import { TarefaRecorrenteExecucaoDeleteDialogComponent } from './tarefa-recorrente-execucao-delete-dialog.component';

describe('TarefaRecorrenteExecucao Management Delete Component', () => {
  let comp: TarefaRecorrenteExecucaoDeleteDialogComponent;
  let fixture: ComponentFixture<TarefaRecorrenteExecucaoDeleteDialogComponent>;
  let service: TarefaRecorrenteExecucaoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TarefaRecorrenteExecucaoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(TarefaRecorrenteExecucaoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TarefaRecorrenteExecucaoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TarefaRecorrenteExecucaoService);
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

jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ContratoFuncionarioService } from '../service/contrato-funcionario.service';

import { ContratoFuncionarioDeleteDialogComponent } from './contrato-funcionario-delete-dialog.component';

describe('ContratoFuncionario Management Delete Component', () => {
  let comp: ContratoFuncionarioDeleteDialogComponent;
  let fixture: ComponentFixture<ContratoFuncionarioDeleteDialogComponent>;
  let service: ContratoFuncionarioService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ContratoFuncionarioDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(ContratoFuncionarioDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ContratoFuncionarioDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ContratoFuncionarioService);
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

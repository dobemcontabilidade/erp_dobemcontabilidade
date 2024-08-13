jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { DescontoPlanoContaAzulService } from '../service/desconto-plano-conta-azul.service';

import { DescontoPlanoContaAzulDeleteDialogComponent } from './desconto-plano-conta-azul-delete-dialog.component';

describe('DescontoPlanoContaAzul Management Delete Component', () => {
  let comp: DescontoPlanoContaAzulDeleteDialogComponent;
  let fixture: ComponentFixture<DescontoPlanoContaAzulDeleteDialogComponent>;
  let service: DescontoPlanoContaAzulService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DescontoPlanoContaAzulDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(DescontoPlanoContaAzulDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DescontoPlanoContaAzulDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DescontoPlanoContaAzulService);
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

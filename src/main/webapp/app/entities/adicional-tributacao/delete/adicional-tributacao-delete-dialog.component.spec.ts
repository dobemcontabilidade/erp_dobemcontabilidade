jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AdicionalTributacaoService } from '../service/adicional-tributacao.service';

import { AdicionalTributacaoDeleteDialogComponent } from './adicional-tributacao-delete-dialog.component';

describe('AdicionalTributacao Management Delete Component', () => {
  let comp: AdicionalTributacaoDeleteDialogComponent;
  let fixture: ComponentFixture<AdicionalTributacaoDeleteDialogComponent>;
  let service: AdicionalTributacaoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AdicionalTributacaoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AdicionalTributacaoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AdicionalTributacaoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AdicionalTributacaoService);
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

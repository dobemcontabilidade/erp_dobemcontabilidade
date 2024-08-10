jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { DescontoPlanoContabilService } from '../service/desconto-plano-contabil.service';

import { DescontoPlanoContabilDeleteDialogComponent } from './desconto-plano-contabil-delete-dialog.component';

describe('DescontoPlanoContabil Management Delete Component', () => {
  let comp: DescontoPlanoContabilDeleteDialogComponent;
  let fixture: ComponentFixture<DescontoPlanoContabilDeleteDialogComponent>;
  let service: DescontoPlanoContabilService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DescontoPlanoContabilDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(DescontoPlanoContabilDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DescontoPlanoContabilDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DescontoPlanoContabilService);
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

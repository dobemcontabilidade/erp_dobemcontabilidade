jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PlanoAssinaturaContabilService } from '../service/plano-assinatura-contabil.service';

import { PlanoAssinaturaContabilDeleteDialogComponent } from './plano-assinatura-contabil-delete-dialog.component';

describe('PlanoAssinaturaContabil Management Delete Component', () => {
  let comp: PlanoAssinaturaContabilDeleteDialogComponent;
  let fixture: ComponentFixture<PlanoAssinaturaContabilDeleteDialogComponent>;
  let service: PlanoAssinaturaContabilService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PlanoAssinaturaContabilDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(PlanoAssinaturaContabilDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PlanoAssinaturaContabilDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PlanoAssinaturaContabilService);
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

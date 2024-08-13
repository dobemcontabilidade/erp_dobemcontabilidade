jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { FeedBackUsuarioParaContadorService } from '../service/feed-back-usuario-para-contador.service';

import { FeedBackUsuarioParaContadorDeleteDialogComponent } from './feed-back-usuario-para-contador-delete-dialog.component';

describe('FeedBackUsuarioParaContador Management Delete Component', () => {
  let comp: FeedBackUsuarioParaContadorDeleteDialogComponent;
  let fixture: ComponentFixture<FeedBackUsuarioParaContadorDeleteDialogComponent>;
  let service: FeedBackUsuarioParaContadorService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FeedBackUsuarioParaContadorDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(FeedBackUsuarioParaContadorDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FeedBackUsuarioParaContadorDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FeedBackUsuarioParaContadorService);
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

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FeedBackUsuarioParaContadorDetailComponent } from './feed-back-usuario-para-contador-detail.component';

describe('FeedBackUsuarioParaContador Management Detail Component', () => {
  let comp: FeedBackUsuarioParaContadorDetailComponent;
  let fixture: ComponentFixture<FeedBackUsuarioParaContadorDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FeedBackUsuarioParaContadorDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: FeedBackUsuarioParaContadorDetailComponent,
              resolve: { feedBackUsuarioParaContador: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FeedBackUsuarioParaContadorDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedBackUsuarioParaContadorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load feedBackUsuarioParaContador on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FeedBackUsuarioParaContadorDetailComponent);

      // THEN
      expect(instance.feedBackUsuarioParaContador()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
